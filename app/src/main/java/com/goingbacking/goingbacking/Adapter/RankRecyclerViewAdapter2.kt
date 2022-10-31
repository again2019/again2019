package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.NewSaveTimeYearDTO
import com.goingbacking.goingbacking.bottomsheet.RankBottomSheet
import com.goingbacking.goingbacking.databinding.ItemRankingBinding

class RankRecyclerViewAdapter2 (
    val onItemClicked : (String) -> Unit
        ): RecyclerView.Adapter<RankRecyclerViewAdapter2.MyViewHolder>() {
    private var newSaveTimeYearDTOList : ArrayList<NewSaveTimeYearDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = newSaveTimeYearDTOList[position]
        if (position == 0)  {
            holder.bind(item, position)
        } else {
            var beforeNum = position-1
            var first = true

            while(true) {
                if (newSaveTimeYearDTOList[position].count!!.equals(newSaveTimeYearDTOList[beforeNum].count!!)) {
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
        return this.newSaveTimeYearDTOList.size
    }
    fun updateList(list: ArrayList<NewSaveTimeYearDTO>) {
        this.newSaveTimeYearDTOList = list
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewSaveTimeYearDTO, position: Int) {

            binding.rankNum.text = (position+1).toString()
            binding.rankCount.text = item.count.toString()
            binding.rankNickname.text = item.nickname.toString()
            binding.rankType.text = item.type.toString()
            binding.rankWhattodo.text = item.whattodo.toString()
            binding.rankButton.setOnClickListener {
                onItemClicked.invoke(item.uid.toString())
            }
        }

    }





}