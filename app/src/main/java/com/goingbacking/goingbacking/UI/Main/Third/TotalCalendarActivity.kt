package com.goingbacking.goingbacking.UI.Main.Third

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle

import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
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
import java.time.temporal.WeekFields
import java.util.*

@AndroidEntryPoint
class TotalCalendarActivity : BaseActivity<ActivityTotalCalendarBinding>({
    ActivityTotalCalendarBinding.inflate(it)
}) {
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private var monthList = mutableListOf<String>()
    private var selectedDateList = listOf<String>()

    val viewModel: ThirdViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuHost: MenuHost = this

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        finish()
                        return true
                    }
                }
                return true
            }
        }, this, Lifecycle.State.RESUMED)


        val job1 = CoroutineScope(Dispatchers.Main).launch {
            for (i in 0..4) {
                monthList.add(YearMonth.now().plusMonths(i.toLong()).toString())
            }
            val example = async{observer2(YearMonth.now())}
            selectedDateList = example.await()

            val daysOfWeek = daysOfWeekFromLocale()
            val currentMonth = YearMonth.now()
            binding.totalthreeCalendar.setup(
                currentMonth.minusMonths(10),
                currentMonth.plusMonths(10),
                daysOfWeek.first()
            )
            binding.totalthreeCalendar.scrollToMonth(currentMonth)
            binding.totalthreeCalendar.post { selectDate(today) }


                binding.totalthreeCalendar.monthHeaderBinder = object :
                    MonthHeaderFooterBinder<MonthViewContainer> {
                    override fun create(view: View) = MonthViewContainer(view)
                    override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                        container.legendLayout.children.map { it as TextView }.forEach {
                            it.text = month.yearMonth.toString()
//                            Log.d("experiment", "monthList " + month.yearMonth.toString())

                        }
                    }

            }

            binding.totalthreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
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
                                dotView.makeGONE()
                            }
                            else -> {
                                textView.background = null
                                if(selectedDateList.contains(day.date.toString())) {
                                    dotView.isVisible = true
                                }
                            }
                        }
                    } else {
                        textView.makeGONE()
                        dotView.makeGONE()
                    }

                }
            }


        }


        binding.totalthreeCalendar.monthScrollListener = {
            CoroutineScope(Dispatchers.Main).launch {
                if (!monthList.contains(it.yearMonth.toString())) {
                    monthList.add(it.yearMonth.toString())
                    val example2 = async{observer1(it.yearMonth.toString())}
                    selectedDateList = selectedDateList + example2.await()
                    Log.d("experiment", "new " + selectedDateList)
                    binding.totalthreeCalendar.notifyMonthChanged(it.yearMonth)

                }
            }
        }

    }



    inner class DayViewContainer(view: View) : ViewContainer(view) {
        lateinit var day: CalendarDay
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
                    Log.d("experiment", "dateList " + selectedDateList.toString())

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

    private suspend fun observer1(yearMonth : String) : List<String> {
        var lis = listOf<String>()
        var success = true
        viewModel.getThirdDateInfo1(yearMonth)
        viewModel.thirdDateDTOs1.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    lis = state.data.dateList
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    Log.e("experiment", state.error.toString())
                    success = false
                }
            }

        }

        Log.d("experiment", "new yearmonth: " + yearMonth.toString())
        Log.d("experiment", "new yearmonth: " + success.toString())

        while (true) {
            if (lis.size != 0 || !success) {
                binding.progressCircular.hide()
                break
            } else {
                delay(100)
                Log.d("experiment", "ssss")
                binding.progressCircular.show()
            }
        }


        return lis
    }


    private suspend fun observer2(year_month:YearMonth) : List<String> {
        var lis = listOf<String>()


        viewModel.getThirdDateInfo2(year_month.toString())
        viewModel.thirdDateDTOs2.observe(this) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    lis = state.data
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                }
            }

        }


        while (true) {
            if (lis.size != 0) {
                binding.progressCircular.hide()
                break
            } else {
                delay(100)
                binding.progressCircular.show()
            }
        }

        return selectedDateList + lis
    }
}