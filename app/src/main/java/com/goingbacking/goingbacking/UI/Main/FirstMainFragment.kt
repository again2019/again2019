package com.goingbacking.goingbacking.UI.Main


import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.goingbacking.goingbacking.Adapter.TodayRecyclerViewAdapter
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.FCM.RetrofitInstance
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Main.First.TmpTimeActivity
import com.goingbacking.goingbacking.databinding.FragmentFirstMainBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FirstMainFragment : BaseFragment<FragmentFirstMainBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstMainBinding {
        return FragmentFirstMainBinding.inflate(inflater, container, false)
    }

    private var myToken : String = ""
    companion object {
        private const val TAG = "experiment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ///
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            myToken = task.result

            Log.d(TAG +"토큰:", myToken)  ///나의 토큰을 알수있는 Firebase 메서드
        })

        binding.tmp.setOnClickListener {
            PushNotification(
                NotificationData("title", "message"),
                "c0UUQlkXSBOpoGfTcrsdEC:APA91bFyufdhpJGJKgShK3ujlSK0GzGrEA2wHkx1uSBxJlsM5MsnR_W0Gj65lVCD0dshOJhMcqvP7dIVXmPt6g_jhTFoSW74s5AyHssT_mYrwRFh02MmLzRqE4p0GdUBBUS__0AI-VgH"
            ).also {
                sendNotification(it)
            }

        }
        ////

        binding.tmpTimeButton.setOnClickListener {
            moveTmpTimePage()
        }

        binding.todayTime.text = PrefUtil.getTodayTotalTime(requireContext()).toString()

        val todayWhatToDo = PrefUtil.getTodayWhatToDo(requireActivity()).toString()
        val todayWhatToDoTime = PrefUtil.getTodayWhatToDoTime(requireActivity()).toString()

       if(!(todayWhatToDo.equals("") || todayWhatToDoTime.equals(""))) {
            val todayWhatToDo2 = todayWhatToDo.replace("[", "").replace("]", "").split(", ")
            val todayWhatToDoTime2 = todayWhatToDoTime.replace("[", "").replace("]", "").split(", ")
            val adapter = TodayRecyclerViewAdapter(requireActivity(), todayWhatToDo2, todayWhatToDoTime2)
            binding.todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.todayRecyclerView.adapter = adapter
        }
    }

    private fun moveTmpTimePage() {
        val intent = Intent(requireActivity(), TmpTimeActivity::class.java)
        startActivity(intent)
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




