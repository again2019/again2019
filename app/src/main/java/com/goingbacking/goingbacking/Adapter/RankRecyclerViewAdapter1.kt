package com.goingbacking.goingbacking.Adapter

import android.animation.Animator
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.FCM.RetrofitInstance
import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Repository.AlarmRepository
import com.goingbacking.goingbacking.Repository.ForthRepository
import com.goingbacking.goingbacking.UI.Main.FirstMainFragment
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.databinding.ItemRankingBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "experiment"

class RankRecyclerViewAdapter1 (
    val forthRepository: ForthRepository,
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
                    forthRepository.likeButtonInfo1(item.uid.toString(), "plus")
                    PushNotification(
                        NotificationData("title", "message"),
                        "c0UUQlkXSBOpoGfTcrsdEC:APA91bFyufdhpJGJKgShK3ujlSK0GzGrEA2wHkx1uSBxJlsM5MsnR_W0Gj65lVCD0dshOJhMcqvP7dIVXmPt6g_jhTFoSW74s5AyHssT_mYrwRFh02MmLzRqE4p0GdUBBUS__0AI-VgH"
                    ).also {
                        sendNotification(it)
                    }
                    isSwitch = false
                    rankLikeNum = rankLikeNum + 1
                    rankLike.text = rankLikeNum.toString()

                } else {
                    likeButton.setMinAndMaxProgress(0f,0f)
                    likeButton.playAnimation()
                    forthRepository.likeButtonInfo1(item.uid.toString(), "minus")
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

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {

            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }








}