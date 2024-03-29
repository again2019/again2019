package com.example.presentation.ui.main.third

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.applikeysolutions.cosmocalendar.model.Day
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.example.presentation.ui.base.BaseFragment
import com.example.domain.util.toast
import com.example.presentation.databinding.FragmentScheduleInput1Binding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ScheduleInputFragment1 : BaseFragment<FragmentScheduleInput1Binding>() {

    private var durationList = mutableListOf<Day>()
    private var yearList = mutableListOf<String>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleInput1Binding {
        return FragmentScheduleInput1Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        toast(requireContext(), "같은 달의 스케줄 입력만 가능합니다.")

        binding.backbtn.setOnClickListener {
            requireActivity().finish()
        }

        binding.durationCalendarView.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            if(binding.durationCalendarView.selectedDates.size <= 0) return@OnDaySelectedListener
            durationList = binding.durationCalendarView.selectedDays
            Log.d("experiment", durationList.toString())
        })

        onClick()

    }
    private fun onClick() = with(binding) {
        nextbtn.setOnClickListener {
            var yearMonth = ""
            var state = true

            if (durationList.size == 0) {
                toast(requireContext(), "날짜를 선택해주세요.")
            } else {

                for (day in 0..(durationList.size-1)) {
                    // 요일
                    Log.d("experiment", day.toString())
                    Log.d("experiment", durationList.get(day).toString())

                    val dayofweek = durationList.get(day).calendar.get(Calendar.DAY_OF_WEEK).toString()
                    // 년
                    val year = durationList.get(day).calendar.get(Calendar.YEAR).toString()
                    // 월
                    var month = (durationList.get(day).calendar.get(Calendar.MONTH) + 1).toString()
                    // 일
                    var dayofmonth = durationList.get(day).calendar.get(Calendar.DAY_OF_MONTH).toString()

                    if (month.length == 1) {
                        month = "0" + month
                    }
                    if (dayofmonth.length == 1) {
                        dayofmonth = "0" + dayofmonth
                    }
                    val curYearMonth = String.format("%s-%s", year, month)
                    if (day == 0) {
                        yearMonth = curYearMonth
                    } else {
                        if (!yearMonth.equals(curYearMonth)) {
                            state = false
                            break
                        }
                    }
                    if (binding.chip1.isChecked && dayofweek.equals("2")) {
                        yearList.add(year + '-' + month + '-' + dayofmonth)
                    }
                    if (binding.chip2.isChecked && dayofweek.equals("3")) {
                        yearList.add(year + '-' + month + '-' + dayofmonth)
                    }
                    if (binding.chip3.isChecked && dayofweek.equals("4")) {
                        yearList.add(year + '-' + month + '-' + dayofmonth)
                    }
                    if (binding.chip4.isChecked && dayofweek.equals("5")) {
                        yearList.add(year + '-' + month + '-' + dayofmonth)
                    }
                    if (binding.chip5.isChecked && dayofweek.equals("6")) {
                        yearList.add(year + '-' + month + '-' + dayofmonth)
                    }
                    if (binding.chip6.isChecked && dayofweek.equals("7")) {
                        yearList.add(year + '-' + month + '-' + dayofmonth)
                    }
                    if (binding.chip7.isChecked && dayofweek.equals("1")) {
                        yearList.add(year + '-' + month + '-' + dayofmonth)
                    }
                }
                if (state) {
                    if (yearList.size == 0) {
                        toast(requireContext(), "요일을 선택해주세요.")
                    } else {

                        val action = ScheduleInputFragment1Directions.actionScheduleInputFragment1ToScheduleInputFragment2(yearList.toTypedArray(), yearMonth)
                        findNavController().navigate(action)
                    }

                } else {
                    toast(requireContext(), "같은 달 내의 날짜를 선택해주세요.")
               }

            }
        }
    }






}