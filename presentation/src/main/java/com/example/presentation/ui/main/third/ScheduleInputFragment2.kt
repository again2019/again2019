package com.example.presentation.ui.main.third

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.model.DateModel
import com.example.domain.model.ScheduleModel
import com.example.presentation.ui.base.BaseFragment
import com.example.domain.util.convertDateToTimeStamp
import com.example.domain.util.toast
import com.example.presentation.NetworkManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentScheduleInput2Binding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ScheduleInputFragment2 : BaseFragment<FragmentScheduleInput2Binding>() {
    private val viewModel : ThirdViewModel by viewModels()
    private var home1time: Int? = null
    private var home2time: Int? = null
    private var dest1time: Int? = null
    private var dest2time: Int? = null
    private val cal = Calendar.getInstance()/////
    // Calendar 객체는 안쓰는게 좋음 메모리 누수 위험성
    private var date = arrayOf<String>()
    private var yearMonth = ""
    private var home2ButtonText = ""
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleInput2Binding {
        return FragmentScheduleInput2Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleInputFragment2_to_scheduleInputFragment1)
        }

        val args : ScheduleInputFragment2Args by navArgs()
        date = args.dateList
        yearMonth = args.yearMonth

        onClick()
    }

        @SuppressLint("ResourceAsColor")
        private fun onClick() = with(binding) {
            home1Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        home1time = (hourOfDay * 60) + minute
                        var hourOfDay_str = hourOfDay.toString()
                        var minute_str = minute.toString()
                        if (hourOfDay / 10 == 0) {
                            hourOfDay_str = '0' + hourOfDay.toString()
                        }
                        if (minute / 10 == 0 ) {
                            minute_str = '0' + minute.toString()
                        }
                        binding.home1Button.text = String.format("%s:%s", hourOfDay_str, minute_str)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }
            home2Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        home2time = (hourOfDay * 60) + minute
                        var hourOfDay_str = hourOfDay.toString()
                        var minute_str = minute.toString()
                        if (hourOfDay / 10 == 0) {
                            hourOfDay_str = '0' + hourOfDay.toString()
                        }
                        if (minute / 10 == 0 ) {
                            minute_str = '0' + minute.toString()
                        }
                        binding.home2Button.text = String.format("%s:%s", hourOfDay_str, minute_str)
                        home2ButtonText = String.format("%s-%s", hourOfDay_str, minute_str)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }
            dest1Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        dest1time = (hourOfDay * 60) + minute
                        var hourOfDay_str = hourOfDay.toString()
                        var minute_str = minute.toString()
                        if (hourOfDay / 10 == 0) {
                            hourOfDay_str = '0' + hourOfDay.toString()
                        }
                        if (minute / 10 == 0 ) {
                            minute_str = '0' + minute.toString()
                        }
                        binding.dest1Button.text = String.format("%s:%s", hourOfDay_str, minute_str)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }
            dest2Button.setOnClickListener {
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        dest2time = (hourOfDay * 60) + minute
                        var hourOfDay_str = hourOfDay.toString()
                        var minute_str = minute.toString()
                        if (hourOfDay / 10 == 0) {
                            hourOfDay_str = '0' + hourOfDay.toString()
                        }
                        if (minute / 10 == 0 ) {
                            minute_str = '0' + minute.toString()
                        }
                        binding.dest2Button.text = String.format("%s:%s", hourOfDay_str, minute_str)
                    }
                TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    false
                ).show()
            }

            finishBtn.setOnClickListener {
                if (!NetworkManager.checkNetworkState(requireContext())) {
                    toast(requireContext(), getString(R.string.network_fail))
                } else {
                    if (destinationPlaceEditText.text.isNullOrEmpty()) {
                        toast(requireActivity(), getString(R.string.destination_input_fail))
                    } else if (home1time == null) {
                        toast(requireActivity(), getString(R.string.first_start_time_fail))
                    } else if (home2time == null) {
                        toast(requireActivity(), getString(R.string.first_end_time_fail))
                    } else if (dest1time == null) {
                        toast(requireActivity(), getString(R.string.second_start_time_fail))
                    } else if (dest2time == null) {
                        toast(requireActivity(), getString(R.string.second_end_time_fail))
                    } else {
                        for (day in date) {
                            val event = ScheduleModel(
                                dest = binding.destinationPlaceEditText.text.toString(),
                                date = day,
                                start = home2time!!,
                                end = dest1time!!,
                                start_t = home2time!! - home1time!!,
                                end_t = dest2time!! - dest1time!!
                            )

                            val path2 =
                                convertDateToTimeStamp(day + "-" + home2ButtonText).toString()

                            val dateDTO = DateModel(
                                dateList = date.toList()
                            )
                            viewModel.addDateInfo(yearMonth, dateDTO)
                            viewModel.addScheduleEventInfo(yearMonth, path2, event)

                            toast(requireContext(), "등록 완료했습니다. 다음 날부터 사용 가능합니다.")
                            requireActivity().finish()
                        }
                    }
                }


            }
        }
}


