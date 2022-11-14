package com.goingbacking.goingbacking.UI.Main.Third

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.applikeysolutions.cosmocalendar.model.Day
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentScheduleInput1Binding
import com.goingbacking.goingbacking.util.toast
import com.google.android.material.chip.Chip
import java.util.*

class ScheduleInputFragment1 : BaseFragment<FragmentScheduleInput1Binding>() {
    private val viewModel : MainViewModel by viewModels()

    private var durationList = mutableListOf<Day>()
    private var checkedList = mutableListOf<String>()
    private var yearList = mutableListOf<String>()

    private var monday : Boolean = false
    private var tuesday : Boolean = false
    private var wednesday : Boolean = false
    private var thursday : Boolean = false
    private var friday : Boolean = false
    private var saturday : Boolean = false
    private var sunday : Boolean = false
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScheduleInput1Binding {
        return FragmentScheduleInput1Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        return true
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.durationCalendarView.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            if(binding.durationCalendarView.selectedDates.size <= 0) return@OnDaySelectedListener
            durationList = binding.durationCalendarView.selectedDays
            Log.d("experiment", durationList.toString())
        })

        if(binding.chip1.isChecked) { monday = true } else { monday = false }
        if(binding.chip2.isChecked) { tuesday = true } else { tuesday = false }
        if(binding.chip3.isChecked) { wednesday = true } else { wednesday = false }
        if(binding.chip4.isChecked) { thursday = true } else { thursday = false }
        if(binding.chip5.isChecked) { friday = true } else { friday = false }
        if(binding.chip6.isChecked) { saturday = true } else { saturday = false }
        if(binding.chip7.isChecked) { sunday = true } else { sunday = false }


        onClick()

    }
    private fun onClick() = with(binding) {
        nextbtn.setOnClickListener {
            var yearMonth = ""
            var state = true

            Log.d("experiment", durationList.size.toString())

            if (durationList.size == 0) {

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

                    Log.d("experiment", "ssss" + dayofweek.toString())


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

                Log.d("experiment", state.toString())
                Log.d("experiment", yearList.toString())


                if (state) {
                    val dateDTO = DateDTO(
                        date = yearList.joinToString(",")
                    )
//                viewModel.addDateInfo(yearMonth, dateDTO)
                } else {
                    toast(requireContext(), "같은 달 내의 날짜를 선택해주세요.")
               }

            }
        }
    }






}