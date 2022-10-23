package com.goingbacking.goingbacking.UI.Main

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.Adapter.CalendarEventAdapter
import com.goingbacking.goingbacking.UI.Main.Third.ScheduleInputActivity
import com.goingbacking.goingbacking.Model.CalendarInfoDTO
import com.goingbacking.goingbacking.Model.Event

import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentThirdMainBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.makeInVisible
import com.goingbacking.goingbacking.util.makeVisible
import com.goingbacking.goingbacking.util.setTextColorRes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.yearMonth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.example_3_calendar_day.*
import kotlinx.android.synthetic.main.example_3_calendar_day.view.*
import kotlinx.android.synthetic.main.example_3_event_item_view.view.*
import kotlinx.android.synthetic.main.fragment_third_main.*
import kotlinx.android.synthetic.main.fragment_third_main.view.*

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*





@AndroidEntryPoint
class ThirdMainFragment : BaseFragment<FragmentThirdMainBinding>() {
    private val eventsAdapter = CalendarEventAdapter {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.example_3_dialog_delete_confirmation)
            .setPositiveButton(R.string.delete) { _, _ ->
               // deleteEvent(it)
            }
            .setNegativeButton(R.string.close, null)
            .show()
    }




    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val selectionFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    private var events = mutableMapOf<LocalDate, List<Event>>()



    val viewModel : MainViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThirdMainBinding {
        return FragmentThirdMainBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getThirdDateInfo()

        observer1()



        binding.exThreeRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        }

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.exThreeCalendar.setup(currentMonth.minusMonths(0), currentMonth.plusMonths(0), daysOfWeek.first())
        binding.exThreeCalendar.scrollToMonth(currentMonth)

        binding.exThreeCalendar2.setup(currentMonth.minusMonths(0), currentMonth.plusMonths(0), daysOfWeek.first())
        binding.exThreeCalendar2.scrollToMonth(currentMonth)

        if (savedInstanceState == null) {
            binding.exThreeCalendar.post {
                // Show today's events initially.
                selectDate(today)
            }
            binding.exThreeCalendar2.post {
                // Show today's events initially.
                selectDate(today)
            }
        }


//-----
        binding.exThreeCalendar2.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.view.exThreeDayText
                val dotView = container.view.exThreeDotView

                textView.text = day.date.dayOfMonth.toString()

                dotView.isVisible = false

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.example_3_white)
                            textView.setBackgroundResource(R.drawable.example_3_today_bg)
                            dotView.makeInVisible()
                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.example_3_blue)
                            textView.setBackgroundResource(R.drawable.example_3_selected_bg)
                            dotView.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.example_3_black)
                            textView.background = null

                            //observer1(day.date, container.view.exThreeDotView)


                            observer2(day.date, dotView)



                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }

            }
        }

        binding.exThreeCalendar2.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].name.first().toString()
                        tv.setTextColorRes(R.color.example_3_black)
                    }
                }
            }
        }

        binding.exThreeCalendar2.monthScrollListener = {


            exThreeSelectedDateText.text = selectionFormatter.format(today)


        }

//---





        binding.exThreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.view.exThreeDayText
                val dotView = container.view.exThreeDotView

                textView.text = day.date.dayOfMonth.toString()

                dotView.isVisible = false

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.example_3_white)
                            textView.setBackgroundResource(R.drawable.example_3_today_bg)
                            dotView.makeInVisible()
                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.example_3_blue)
                            textView.setBackgroundResource(R.drawable.example_3_selected_bg)
                            dotView.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.example_3_black)
                            textView.background = null

                            //observer1(day.date, container.view.exThreeDotView)
                            observer2(day.date, dotView)
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }

            }
        }

        binding.exThreeCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }.forEachIndexed { index, tv ->
                        tv.text = daysOfWeek[index].name.first().toString()
                        tv.setTextColorRes(R.color.example_3_black)
                    }
                }
            }
        }

        binding.exThreeCalendar.monthScrollListener = {


            exThreeSelectedDateText.text = selectionFormatter.format(today)


        }


        binding.exThreeAddButton.setOnClickListener {
            var intent = Intent(activity, ScheduleInputActivity::class.java)
            startActivity(intent)
        }

        binding.weekModeCheckBox.setOnCheckedChangeListener { _, monthToWeek ->
            if (!monthToWeek) {
                binding.exThreeCalendar.updateMonthConfiguration(
                    inDateStyle = InDateStyle.FIRST_MONTH,
                    maxRowCount = 1,
                    hasBoundaries = true
                )
                binding.exThreeCalendar2.scrollToDate(today)
            } else {
                binding.exThreeCalendar2.updateMonthConfiguration(
                    inDateStyle = InDateStyle.ALL_MONTHS,
                    maxRowCount = 6,
                    hasBoundaries = true
                )
                binding.exThreeCalendar2.scrollToDate(today)

            }

//            val firstDate = exThreeCalendar.findFirstVisibleDay()?.date ?: return@setOnCheckedChangeListener
//            val lastDate = exThreeCalendar.findLastVisibleDay()?.date ?: return@setOnCheckedChangeListener
//
//            val oneWeekHeight = exThreeCalendar.daySize.height
//            val oneMonthHeight = oneWeekHeight * 6
//
//            val oldHeight = if (monthToWeek) oneMonthHeight else oneWeekHeight
//            val newHeight = if (monthToWeek) oneWeekHeight else oneMonthHeight
//
//
//            if (!monthToWeek) {
//                exThreeCalendar.updateMonthConfiguration(
//                    inDateStyle = InDateStyle.ALL_MONTHS,
//                    maxRowCount = 6,
//                    hasBoundaries = true
//                )
//                if (firstDate.yearMonth == lastDate.yearMonth) {
//                        exThreeCalendar.scrollToMonth(firstDate.yearMonth)
//                    } else {
//                        // We compare the next with the last month on the calendar so we don't go over.
//                        exThreeCalendar.scrollToMonth(minOf(firstDate.yearMonth.next, currentMonth.plusMonths(10)))
//                    }
//            } else {
//                exThreeCalendar.updateMonthConfiguration(
//                        inDateStyle = InDateStyle.FIRST_MONTH,
//                        maxRowCount = 1,
//                        hasBoundaries = false
//                    )
//                exThreeCalendar.scrollToDate(today)
//            }
//        }

        }
    }

    private fun observer1() {
        viewModel.thirdDateDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    val data = state.data.date.toString().split(',').toMutableList()
                    saveEvent(data)

                    }




                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }
        }


    }

    private fun observer2(date:LocalDate, dotView:View) {

        viewModel.thirdDateDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    val data = state.data.date.toString().split(',')

                    if(data!!.contains(date.toString())) {

                                        dotView.isVisible = true
                                    }
                    Log.d("experiment", "observer2: " + state.data.date.toString().split(',').toString())


                }
                is UiState.Failure -> {
                    Log.e("experiment",state.error.toString())
                }
            }

        }
    }

    private fun observer3(yearList : MutableList<String>) {
        viewModel.getThirdCalendarInfo(yearList)
        viewModel.thirdCalendarDTOs.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    events = state.data
                    updateAdapterForDate(LocalDate.now())

                }




                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }
        }


    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { exThreeCalendar.notifyDateChanged(it) }
            exThreeCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)
            exThreeSelectedDateText.text = selectionFormatter.format(date)

        }
    }

    private fun saveEvent(yearList : MutableList<String>) {
        observer3(yearList)
        Log.d("experiment", "now: " + LocalDate.now().toString())



    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        lateinit var day: CalendarDay // Will be set when this container is bound.
        init {
            view.setOnClickListener {
                if (day.owner == DayOwner.THIS_MONTH) {
                    selectDate(day.date)
                }
            }

        }
    }

    inner class MonthViewContainer(view: View) : ViewContainer(view) {
        val legendLayout = view.findViewById<LinearLayout>(R.id.legendLayout)
    }



//    private fun deleteEvent(event: Event) {
//        val date = event.date
//        events[date] = events[date].orEmpty().minus(event)
//        updateAdapterForDate(date)
//    }

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




