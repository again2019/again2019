package com.goingbacking.goingbacking.UI.Main.First


import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.goingbacking.goingbacking.Adapter.TodayRecyclerViewAdapter
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.FCM.RetrofitInstance
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Main.Third.ScheduleInputActivity
import com.goingbacking.goingbacking.UI.Main.Third.TotalCalendarActivity
import com.goingbacking.goingbacking.databinding.FragmentFirstMainBinding
import com.goingbacking.goingbacking.util.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FirstMainFragment : BaseFragment<FragmentFirstMainBinding>() {

    val viewModel: FirstViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstMainBinding {
        return FragmentFirstMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.tmpTimeButton.setMinAndMaxProgress(0f, 1f)
        binding.tmpTimeButton.playAnimation()
        binding.tmpTimeButton.setOnClickListener {
            moveTmpTimePage()
        }


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

        //binding.todayText.makeInVisible()
        binding.todayTextHide.makeVisible()

        binding.todayTextHide.setOnClickListener {
            binding.todayTextHide.makeGONE()
            binding.todayText.makeVisible()
        }

        binding.addPlanButton.setOnClickListener {
            moveAddPlanPage()
        }
        binding.goThirdButton.setOnClickListener {
            moveTotalCalendarPage()
        }


    }

    private fun observer() {
        viewModel.getTmpTimeInfo()
        viewModel.tmpTimeDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    val tmpTimeCount = state.data.size
                    if (tmpTimeCount == 0) {
                        binding.tmpTimeStateTextView.text = "저장해야 하는 시간이 없어요"
                    }
                    binding.tmpTimeStateTextView.text = "저장하지 않은 시간이 " + tmpTimeCount.toString() + "개 있어요."
                }
                is UiState.Failure -> {

                }
                is UiState.Loading -> {

                }
            }

        }
    }

    private fun moveTmpTimePage() {
        val intent = Intent(requireActivity(), TmpTimeActivity::class.java)
        startActivity(intent)
    }

    private fun moveAddPlanPage() {
        val intent = Intent(requireActivity(), ScheduleInputActivity::class.java)
        startActivity(intent)
    }

    private fun moveTotalCalendarPage() {
        val intent = Intent(requireActivity(), TotalCalendarActivity::class.java)
        startActivity(intent)
    }


}




