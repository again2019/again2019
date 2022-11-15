package com.goingbacking.goingbacking.UI.Main.Third

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.core.view.isVisible

import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.bottomsheet.CalendarDetailBottomSheet
import com.goingbacking.goingbacking.databinding.ActivityTotalCalendarBinding
import com.goingbacking.goingbacking.databinding.ItemCalendarDayBinding
import com.goingbacking.goingbacking.databinding.ItemCalendarHeaderBinding
import com.goingbacking.goingbacking.util.*
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
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

    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.totalthreeCalendar.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            daysOfWeek.first()
        )
        binding.totalthreeCalendar.scrollToMonth(currentMonth)
        binding.totalthreeCalendar.post { selectDate(today) }

        binding.totalthreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exThreeDayText
                val dotView = container.binding.exThreeDotView
                textView.text = day.date.dayOfMonth.toString()
                dotView.isVisible = false

                //Log.d("experiment", "okay2")

                if (day.owner == DayOwner.THIS_MONTH) {
                    var year_month = ""
                    if (day.date.monthValue / 10 == 1) {
                        year_month = day.date.year.toString() + "-" + day.date.monthValue.toString()
                    } else {
                        year_month = day.date.year.toString() + "-0" + day.date.monthValue.toString()
                    }

                    //Log.e("experiment","day.owner: " + year_month + " " + day.date.dayOfMonth)

                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setBackgroundResource(R.drawable.today_rectangle)
                            dotView.makeGONE()
                        }
                        selectedDate -> {
                            textView.setBackgroundResource(R.drawable.selected_rectangle)
                            dotView.makeGONE()
                        }
                        else -> {
                            textView.background = null

                            observer2(day.date, dotView, year_month)
                        }
                    }
                } else {
                    textView.makeGONE()
                    dotView.makeGONE()
                }

            }
        }

        binding.totalthreeCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                    container.legendLayout.children.map { it as TextView }.forEach {
                        it.text = month.yearMonth.toString()
                        it.setTextColorRes(R.color.example_3_black)
                    }
            }
        }

        binding.totalthreeCalendar.monthScrollListener = {}

    }


    inner class DayViewContainer(view: View) : ViewContainer(view) {
        lateinit var day: CalendarDay // Will be set when this container is bound.
        val binding = ItemCalendarDayBinding.bind(view)

        init {
            view.setOnClickListener {
                if (day.owner == DayOwner.THIS_MONTH) {
                    selectDate(day.date)
                    val bottom = CalendarDetailBottomSheet()
                    val bundle = Bundle()
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
            oldDate?.let { binding.totalthreeCalendar.notifyDateChanged(it) }
            binding.totalthreeCalendar.notifyDateChanged(date)

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


    private fun observer2(date :LocalDate, dotView:View, year_month:String) {
        // date : LocalDate, dotView: View,
        // 첫번 째로 실행 후
        viewModel.getThirdDateInfo2(year_month)


        // 두 번째로 실행
        viewModel.thirdDateDTOs2.observe(this) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()

                    val data = state.data.date.toString().split(',')

                    if(data.contains(date.toString())) {

                        dotView.makeVisible()
                    }


                }
                    is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    //Log.e("experiment",state.error.toString())
                }
            }

        }
    }
}