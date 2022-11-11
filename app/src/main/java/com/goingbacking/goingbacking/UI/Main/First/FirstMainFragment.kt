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
import com.goingbacking.goingbacking.bottomsheet.CheerBottomSheet
import com.goingbacking.goingbacking.databinding.FragmentFirstMainBinding
import com.goingbacking.goingbacking.util.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.skydoves.balloon.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FirstMainFragment : BaseFragment<FragmentFirstMainBinding>() {

    val viewModel: FirstViewModel by viewModels()
    private lateinit var balloon :Balloon

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstMainBinding {
        return FragmentFirstMainBinding.inflate(inflater, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        
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

        onClick()



    }

    private fun onClick() = with(binding) {

        myMessage.setOnClickListener {
            val bottom = CheerBottomSheet()
            val bundle = Bundle()
            bundle.putString("destinationUid", PrefUtil.getCurrentUid(requireContext()))
            bottom.arguments = bundle
            bottom.show(childFragmentManager, bottom.tag)
        }

        tmpTimeButton.setOnClickListener {
            moveTmpTimePage()
        }

        todayTextHide.makeVisible()
        todayTextHide.setOnClickListener {
            todayTextHide.makeGONE()
            todayText.makeVisible()
        }

        addPlanButton.setOnClickListener {
            moveAddPlanPage()
        }
        goThirdButton.setOnClickListener {
            moveTotalCalendarPage()
        }

    }

    private fun observer() {
        viewModel.getTmpTimeInfo()
        viewModel.tmpTimeDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    val tmpTimeCount = state.data.size
                    if (tmpTimeCount == 0) {
                        binding.tmpTimeButton.setMinAndMaxProgress(1f, 1f)
                        binding.tmpTimeDot.makeGONE()
                        balloon = balloonBuild(getString(R.string.first_no_store))
                    } else {
                        binding.tmpTimeButton.setMinAndMaxProgress(0f, 1f)
                        binding.tmpTimeButton.repeatCount = 50
                        binding.tmpTimeDot.makeVisible()
                        balloon = balloonBuild(getString(R.string.first_yes_store1) + tmpTimeCount.toString() + getString(R.string.first_yes_store2))


                    }
                    balloon.showAlignBottom(binding.tmpTimeButton)
                    balloon.dismissWithDelay(2000)
                    binding.tmpTimeButton.playAnimation()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(requireContext(), getString(R.string.first_tmp_fail))
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
            }

        }
    }

    private fun balloonBuild(string: String) : Balloon {
        val balloon = Balloon.Builder(requireContext())
            .setHeight(BalloonSizeSpec.WRAP)
            .setWidth(BalloonSizeSpec.WRAP)
            .setText(string)
            .setTextColorResource(R.color.white)
            .setTextSize(15f)
            .setArrowSize(10)
            .setPadding(12)
            .setArrowPosition(0.5f)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.colorPrimaryDark)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setIconDrawableResource(R.drawable.dot2)
            .build()
        return balloon
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




