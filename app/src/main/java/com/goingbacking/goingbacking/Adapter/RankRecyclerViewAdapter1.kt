package com.goingbacking.goingbacking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.FCM.FirebaseTokenManager
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Repository.Forth.ForthRepository
import com.goingbacking.goingbacking.databinding.ItemRankingBinding
import com.goingbacking.goingbacking.util.PrefUtil


class RankRecyclerViewAdapter1 (
    val forthRepository: ForthRepository,
    val onCheerClicked : (String) -> Unit,
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
                    forthRepository.likeButtonInfo(item.uid.toString(), "plus")
                    PushNotification(
                        NotificationData("title", "message"),
                        "fI__GGumQOm6prMcLJloqr:APA91bHgw1oLKuFq09roGcrKFX3dde_eXv1C_aUUjkzqGpbww-qATujqCM3diqdZZuvBw6tVOLjhDx1zYL5BqQW4THCnpfyihPgWKCsXrX8OhUKMeW6dM1vzHjse0FjCXG782JfzI1oo"
                    ).also {
                        FirebaseTokenManager.sendNotification(it)
                    }
                    isSwitch = false
                    rankLikeNum = rankLikeNum + 1
                    rankLike.text = rankLikeNum.toString()

                } else {
                    likeButton.setMinAndMaxProgress(0f,0f)
                    likeButton.playAnimation()
                    forthRepository.likeButtonInfo(item.uid.toString(), "minus")
                    isSwitch = true
                    rankLikeNum = rankLikeNum - 1
                    rankLike.text = rankLikeNum.toString()

                }

            }

            cheerButton.setOnClickListener {
                onCheerClicked.invoke(item.uid.toString())

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