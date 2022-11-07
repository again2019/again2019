package com.goingbacking.goingbacking.UI.Main.First


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
import com.goingbacking.goingbacking.databinding.FragmentFirstMainBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.makeGONE
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tmpTimeButton.setOnClickListener {
            moveTmpTimePage()
        }


        binding.todayTime.text = PrefUtil.getTodayTotalTime(requireContext()).toString()
        val todayTime = PrefUtil.getTodayTotalTime(requireContext())

        binding.todayHour.text = (todayTime / 60).toString()
        binding.todayMinute.text = (todayTime % 60).toString()
        val todayWhatToDo = PrefUtil.getTodayWhatToDo(requireActivity()).toString()
        val todayWhatToDoTime = PrefUtil.getTodayWhatToDoTime(requireActivity()).toString()

       if(!(todayWhatToDo.equals("[]") || todayWhatToDoTime.equals("[]"))) {
           binding.noPlanTextView.makeGONE()
           val todayWhatToDo2 = todayWhatToDo.removeSurrounding("[", "]").split(", ")
            val todayWhatToDoTime2 = todayWhatToDoTime.removeSurrounding("[", "]").split(", ")
            val adapter = TodayRecyclerViewAdapter(requireActivity(), todayWhatToDo2, todayWhatToDoTime2)
            binding.todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.todayRecyclerView.adapter = adapter
        } else {
            binding.todayScrollView.makeGONE()
       }
    }

    private fun moveTmpTimePage() {
        val intent = Intent(requireActivity(), TmpTimeActivity::class.java)
        startActivity(intent)
    }


}




