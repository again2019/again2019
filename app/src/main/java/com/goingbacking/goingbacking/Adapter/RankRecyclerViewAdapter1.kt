package com.goingbacking.goingbacking.Adapter

import android.animation.Animator
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Repository.AlarmRepository
import com.goingbacking.goingbacking.Repository.ForthRepository
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.databinding.ItemRankingBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RankRecyclerViewAdapter1 (
    val viewModel : ForthViewModel,
    val onItemClicked : (String) -> Unit
        ): RecyclerView.Adapter<RankRecyclerViewAdapter1.MyViewHolder>() {
    private var newSaveTimeMonthList : ArrayList<NewSaveTimeMonthDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = newSaveTimeMonthList[position]

        if (position == 0)  {
            holder.bind(item, position)
        } else {
            var beforeNum = position-1
            var first = true

            while(true) {
                if (newSaveTimeMonthList[position].count!!.equals(newSaveTimeMonthList[beforeNum].count!!)) {
                    beforeNum = beforeNum - 1
                    first = false
                    if (beforeNum == -1) {
                        beforeNum = -1
                        break
                    }
                } else {

                    break
                }
            }

            if (first) {
                holder.bind(item, position)
            } else {
                holder.bind(item, beforeNum+1)
            }

        }



    }

    override fun getItemCount(): Int {
        return this.newSaveTimeMonthList.size
    }
    fun updateList(list: ArrayList<NewSaveTimeMonthDTO>) {
        this.newSaveTimeMonthList = list
        notifyDataSetChanged()
    }


    inner class MyViewHolder(val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewSaveTimeMonthDTO, position: Int) = with(binding) {

            var isSwitch = true

            likeButton.setOnClickListener {
                if (isSwitch) {
                    likeButton.setMinAndMaxProgress(0f, 1f)
                    likeButton.playAnimation()
                    isSwitch = false
                } else {
                    likeButton.setMinAndMaxProgress(0f,0f)
                    likeButton.playAnimation()
                    isSwitch = true
                }

            }
            rankLike.text = item.likes.size.toString()


            rankNum.text = (position+1).toString()
            rankCount.text = item.count.toString()
            rankNickname.text = item.nickname.toString()
            rankType.text = item.nickname.toString()
            rankWhattodo.text = item.whattodo.toString()
            rankButton.setOnClickListener {
                onItemClicked.invoke(item.uid.toString())
            }
        }

    }








}