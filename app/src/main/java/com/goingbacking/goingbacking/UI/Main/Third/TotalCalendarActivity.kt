package com.goingbacking.goingbacking.UI.Main.Third

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.core.view.isVisible

import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.UI.Main.ThirdMainFragment
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.bottomsheet.CalendarDetailBottomSheet
import com.goingbacking.goingbacking.databinding.ActivityTotalCalendarBinding
import com.goingbacking.goingbacking.databinding.ItemCalendarDayBinding
import com.goingbacking.goingbacking.databinding.ItemCalendarHeaderBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.makeInVisible
import com.goingbacking.goingbacking.util.makeVisible
import com.goingbacking.goingbacking.util.setTextColorRes
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
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
class TotalCalendarActivity : BaseActivity<ActivityTotalCalendarBinding>({
    ActivityTotalCalendarBinding.inflate(it)
}) {
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val selectionFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    private var events = mutableMapOf<LocalDate, List<Event>>()
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.exThreeCalendar.setup(
            currentMonth.minusMonths(50),
            currentMonth.plusMonths(50),
            daysOfWeek.first()
        )
        binding.exThreeCalendar.scrollToMonth(currentMonth)
        binding.exThreeCalendar.post { selectDate(today) }

        binding.exThreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exThreeDayText
                val dotView = container.binding.exThreeDotView
                textView.text = day.date.dayOfMonth.toString()
                dotView.isVisible = false

                if (day.owner == DayOwner.THIS_MONTH) {
                    var year_month = ""
                    if (day.date.monthValue / 10 == 1) {
                        year_month = day.date.year.toString() + "-" + day.date.monthValue.toString()
                    } else {
                        year_month = day.date.year.toString() + "-0" + day.date.monthValue.toString()
                    }

                    Log.e("experiment","day.owner: " + year_month + " " + day.date.dayOfMonth)

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

                            observer2(day.date, dotView, year_month)
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
                container.legendLayout.children.map { it as TextView }.forEach {
                    it.text = month.yearMonth.toString()
                    it.setTextColorRes(R.color.example_3_black)
                }
            }
        }

        binding.exThreeCalendar.monthScrollListener = {


        }

    }


    inner class DayViewContainer(view: View) : ViewContainer(view) {
        lateinit var day: CalendarDay // Will be set when this container is bound.
        val binding = ItemCalendarDayBinding.bind(view)

        init {
            view.setOnClickListener {
                if (day.owner == DayOwner.THIS_MONTH) {
                    selectDate(day.date)
                    val bottom = CalendarDetailBottomSheet()
                    var bundle = Bundle()
                    bundle.putString("date", day.date.toString())
                    bottom.arguments = bundle
                    bottom.show(supportFragmentManager, bottom.tag)
                }
            }

        }
    }

    inner class MonthViewContainer(view: View) : ViewContainer(view) {
        val legendLayout = ItemCalendarHeaderBinding.bind(view).legendLayout.root
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.exThreeCalendar.notifyDateChanged(it) }
            binding.exThreeCalendar.notifyDateChanged(date)

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


    private fun observer2(date:LocalDate, dotView:View, year_month:String) {
        viewModel.getThirdDateInfo2(year_month)
        viewModel.thirdDateDTOs2.observe(this) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    val data = state.data.date.toString().split(',')

                    if(data!!.contains(date.toString())) {

                        dotView.isVisible = true
                    }
                    Log.d("experiment", "observer2: " + state.data.date.toString().split(',').toString())
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
}