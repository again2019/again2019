package com.goingbacking.goingbacking.UI.Main.Third

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Adapter.CalendarEventAdapter1
import com.goingbacking.goingbacking.Model.Event

import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentThirdMainBinding
import com.goingbacking.goingbacking.databinding.ItemCalendarDayBinding
import com.goingbacking.goingbacking.databinding.ItemCalendarHeaderMainBinding
import com.goingbacking.goingbacking.util.*


import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer

import dagger.hilt.android.AndroidEntryPoint


import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*





@AndroidEntryPoint
class ThirdMainFragment : BaseFragment<FragmentThirdMainBinding>() {
    private val eventsAdapter = CalendarEventAdapter1 { eventDate, start ->
        val hour = start / 60
        val minute = start % 60
        val route = String.format("%s-%d-%d", eventDate, hour, minute)

        AlertDialog.Builder(requireContext())
            .setMessage("해당 스케줄을 삭제하시겠습니까?")
            .setPositiveButton("삭제하기") { _, _ ->
               deleteEvent(eventDate, convertDateToTimeStamp(route).toString())
                selectedDateList.remove(eventDate)
                binding.threeCalendar.notifyDateChanged(LocalDate.parse(eventDate, DateTimeFormatter.ISO_DATE))
            }
            .setNegativeButton("나가기", null)
            .show()
    }
    
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val selectionFormatter = DateTimeFormatter.ofPattern("MM/dd(E)")
    private var events = mutableMapOf<LocalDate, List<Event>>()
    private var selectedDateList = mutableListOf<String>()


    val viewModel : ThirdViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThirdMainBinding {
        return FragmentThirdMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer4()


        binding.threeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter

        }

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.threeCalendar.setup(currentMonth.minusMonths(0), currentMonth.plusMonths(0), daysOfWeek.first())
        binding.threeCalendar.scrollToMonth(currentMonth)
        binding.threeCalendar.post { selectDate(today) }
        binding.threeCalendar.inDateStyle = InDateStyle.FIRST_MONTH
        binding.threeCalendar.maxRowCount = 1
        binding.threeCalendar.hasBoundaries = true

        binding.threeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exThreeDayText
                val dotView = container.binding.exThreeDotView
                textView.text = day.date.dayOfMonth.toString()
                dotView.isVisible = false



                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setBackgroundResource(R.drawable.today_rectangle)
                            dotView.makeGONE()
                        }
                        selectedDate -> {
                            textView.setBackgroundResource(R.drawable.selected_rectangle)

                        }
                        else -> {

                            textView.background = null
                            observer2(day.date, dotView)
                        }
                    }
                } else {
                    textView.makeGONE()
                    dotView.makeGONE()
                }

            }
        }

        binding.threeCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
            }
        }




        binding.threeCalendar.scrollToDate(today)

        binding.threeAddButton.setOnClickListener {
            val intent = Intent(requireContext(), ScheduleInputActivity::class.java)
            startActivity(intent)
        }


        binding.calendarMode.setOnClickListener {
            val intent = Intent(requireContext(), TotalCalendarActivity::class.java)
            startActivity(intent)
        }
    }





    private fun observer2(date:LocalDate, dotView:View) {
        Log.d("experiment", date.toString())
        viewModel.getThirdDateInfo(currentday("yyyy-MM"))
        viewModel.thirdDateDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    selectedDateList = state.data.dateList.toMutableList()

                    if(selectedDateList.contains(date.toString())) {
                        dotView.isVisible = true
                    }
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    Log.e("experiment",state.error.toString())
                }
            }

        }
    }



    private fun observer4() {
        viewModel.getNickNameInfo()
        viewModel.nickNameInfoDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    binding.myNickName.text = state.data
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }

                is UiState.Failure -> {
                    binding.progressCircular.hide()
                }
            }

        }
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.threeCalendar.notifyDateChanged(it) }
            binding.threeCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)
            binding.threeSelectedDateText.text = selectionFormatter.format(date)

        }
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        lateinit var day: CalendarDay // Will be set when this container is bound.
        val binding = ItemCalendarDayBinding.bind(view)
        init {
            view.setOnClickListener {
                if (day.owner == DayOwner.THIS_MONTH) {
                    selectDate(day.date)

                    observer(currentday("yyyy-MM"), day.date)

                }
            }

        }
    }

    private fun observer(currentday: String, date: LocalDate) {
        viewModel.getSelectedDateInfo(currentday, date.toString())
        viewModel.thirdSelectedDateDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    events = state.data
                    Log.d("experiment", events.toString())
                    if (events.size == 0) {
                        binding.threeRecyclerView.makeGONE()
                    } else {
                        updateAdapterForDate(date)
                        binding.threeRecyclerView.makeVisible()
//                        binding.noScheduleTextView.makeGONE()
                    }

                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()

                }
            }

        }

    }

    inner class MonthViewContainer(view: View) : ViewContainer(view) {
        val legendLayout = ItemCalendarHeaderMainBinding.bind(view).legendLayout.root
    }



    private fun deleteEvent(eventDate: String, route : String) {
        viewModel.deleteThirdCalendarInfo(eventDate, route)
        viewModel.deleteThirdCalendarDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    events = state.data
                    Log.d("experiment", events.toString())
                    if (events.size == 0) {
                        binding.threeRecyclerView.makeGONE()
                    } else {
                        updateAdapterForDate(selectedDate!!)
                        binding.threeRecyclerView.makeVisible()
//                        binding.noScheduleTextView.makeGONE()
                    }

                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()

                }
            }

        }

    }

    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.apply {
            events.clear()
            events.addAll(this@ThirdMainFragment.events[date].orEmpty())
            notifyDataSetChanged()
        }
    }


    fun daysOfWeekFromLocale(): Array<DayOfWeek> {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        var daysOfWeek = DayOfWeek.values()
        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            daysOfWeek = rhs + lhs
        }
        return daysOfWeek
    }




}




