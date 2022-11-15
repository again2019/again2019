package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.FCM.FirebaseTokenManager
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.Model.NewSaveTimeYearDTO
import com.goingbacking.goingbacking.UI.Main.Forth.ForthViewModel
import com.goingbacking.goingbacking.databinding.ItemRankingBinding
import com.goingbacking.goingbacking.util.PrefUtil

class RankRecyclerViewAdapter2 (
    val viewModel: ForthViewModel,
    val onItemClicked : (String) -> Unit
        ): RecyclerView.Adapter<RankRecyclerViewAdapter2.MyViewHolder>() {
    var newSaveTimeYearDTOList : ArrayList<NewSaveTimeYearDTO> = arrayListOf()

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

    inner class MyViewHolder(val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewSaveTimeYearDTO, position: Int) = with(binding) {

            var rankLikeNum = item.likes.size
            rankLike.text = rankLikeNum.toString()
            var isSwitch = true
            if (item.likes.contains(PrefUtil.firebaseUid())) {
                likeButton.setMinAndMaxProgress(1f, 1f)
                likeButton.playAnimation()
                isSwitch = false
            } else {
                likeButton.setMinAndMaxProgress(0f,0f)
                likeButton.playAnimation()

            }
            likeButton.setOnClickListener {
                if (isSwitch) {
                    likeButton.setMinAndMaxProgress(1f, 1f)
                    likeButton.playAnimation()
                    PushNotification(
                        NotificationData("title", "message"),
                        item.token!!
                        ).also {
                        FirebaseTokenManager.sendNotification(it)
                    }
                    isSwitch = false
                    rankLikeNum = rankLikeNum + 1
                    rankLike.text = rankLikeNum.toString()

                } else {
                    likeButton.setMinAndMaxProgress(0f,0f)
                    likeButton.playAnimation()
                    isSwitch = true
                    rankLikeNum = rankLikeNum - 1
                    rankLike.text = rankLikeNum.toString()

                }

            }



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