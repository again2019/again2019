package com.example.presentation.ui.main.first


import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.util.UiState
import com.example.domain.util.makeGONE
import com.example.domain.util.makeVisible
import com.example.domain.util.toast
import com.example.presentation.NetworkManager
import com.example.presentation.PrefUtil
import com.example.presentation.R
import com.example.presentation.adapter.TodayRecyclerViewAdapter
import com.example.presentation.databinding.FragmentFirstMainBinding


import com.example.presentation.ui.base.BaseFragment
import com.example.presentation.ui.datastore.DataStoreViewModel
import com.example.presentation.ui.main.third.ScheduleInputActivity
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class FirstMainFragment : BaseFragment<FragmentFirstMainBinding>() {

    private val viewModel: FirstViewModel by viewModels()







    private val dataStoreViewModel : DataStoreViewModel by viewModels()

    private lateinit var balloon : Balloon

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstMainBinding {
        return FragmentFirstMainBinding.inflate(inflater, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        lifecycleScope.launchWhenStarted {
//            dataStoreViewModel.getTodayTotalTimeFromProto()
//            dataStoreViewModel.getTodayTotalTimeFromProto.collect { state ->
//                    when(state) {
//                        is UiState.Loading -> {
//                            binding.progressCircular.show()
//                        }
//                        is UiState.Success -> {
//                            binding.progressCircular.hide()
//                            val todayTime = state.data!!
//                            binding.todayHour.text = (todayTime / 60).toString()
//                            binding.todayMinute.text = (todayTime % 60).toString()
//                        }
//                        is UiState.Failure -> {
//                            binding.progressCircular.hide()
//                            toast(requireContext(), "failure")
//                        }
//                    }
//                }

                dataStoreViewModel.getTodayTotalTimeFromPreferences()
                dataStoreViewModel.getTodayTotalTimeFromPreferences.collect { state ->
                    when(state) {
                        is UiState.Loading -> {
                            binding.progressCircular.show()
                        }
                        is UiState.Success -> {
                            binding.progressCircular.hide()
                            val todayTime = state.data!!
                            binding.todayHour.text = (todayTime / 60).toString()
                            binding.todayMinute.text = (todayTime % 60).toString()
                        }
                        is UiState.Failure -> {
                            binding.progressCircular.hide()
                            toast(requireContext(), "failure")
                        }
                    }
                }


        }

        val todayWhatToDo = PrefUtil.getTodayWhatToDo(requireActivity()).toString()
        val todayWhatToDoTime = PrefUtil.getTodayWhatToDoTime(requireActivity()).toString()

       if((todayWhatToDo.length <= 2 || todayWhatToDoTime.length <= 2)) {

           binding.todayRecyclerView.makeGONE()
           binding.noPlanTextView.makeVisible()

        } else {
           binding.noPlanTextView.makeGONE()
           val todayWhatToDo2 = todayWhatToDo.removeSurrounding("[", "]").split(", ")
           val todayWhatToDoTime2 = todayWhatToDoTime.removeSurrounding("[", "]").split(", ")
           val adapter = TodayRecyclerViewAdapter(requireActivity(), todayWhatToDo2, todayWhatToDoTime2)
           binding.todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
           binding.todayRecyclerView.adapter = adapter
       }

        onClick()



    }

    private fun onClick() = with(binding) {



        tmpTimeButton.setOnClickListener {
            moveTmpTimePage()
        }

        todayTextHide.makeVisible()
        todayTextHide.setOnClickListener {
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                todayTextHide.makeGONE()
                todayText.makeVisible()
            }
        }

        addPlanButton.setOnClickListener {
            moveAddPlanPage()
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
                        balloon = balloonBuild(getString(R.string.first_yes_store1) + " " + tmpTimeCount.toString() + getString(R.string.first_yes_store2))


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
            .setTextSize(13f)
            .setArrowSize(10)
            .setPadding(12)
            .setArrowPosition(0.5f)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.colorPrimaryDark)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setIconDrawableResource(R.drawable.dot2)
            .setIconSize(18)
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
}




