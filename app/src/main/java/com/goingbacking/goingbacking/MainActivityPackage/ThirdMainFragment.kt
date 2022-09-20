package com.goingbacking.goingbacking.MainActivityPackage

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goingbacking.goingbacking.LoginActivity
import com.goingbacking.goingbacking.MainActivityPackage.ThirdMainFragmentPackage.ScheduleInputActivity
import com.goingbacking.goingbacking.Model.CalendarInfoDTO
import com.goingbacking.goingbacking.Model.Event

import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.yearMonth
import kotlinx.android.synthetic.main.example_3_calendar_day.*
import kotlinx.android.synthetic.main.example_3_calendar_day.view.*
import kotlinx.android.synthetic.main.example_3_event_item_view.view.*
import kotlinx.android.synthetic.main.fragment_third_main.*
import kotlinx.android.synthetic.main.fragment_third_main.view.*
import java.lang.Thread.sleep
import java.sql.Time

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*






class ThirdMainFragment : Fragment() {
    private val eventsAdapter = Example3EventsAdapter {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.example_3_dialog_delete_confirmation)
            .setPositiveButton(R.string.delete) { _, _ ->
               // deleteEvent(it)
            }
            .setNegativeButton(R.string.close, null)
            .show()
    }

    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var calendarInfoDTO : CalendarInfoDTO? = null


    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val events = mutableMapOf<LocalDate, List<Event>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_third_main, container, false)

        init()


        view.exThreeRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = eventsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        }

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        view.exThreeCalendar.setup(currentMonth.minusMonths(1), currentMonth.plusMonths(1), daysOfWeek.first())
        view.exThreeCalendar.scrollToMonth(currentMonth)

        if (savedInstanceState == null) {
            view.exThreeCalendar.post {
                // Show today's events initially.
                selectDate(today)
            }
        }


        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                        selectDate(day.date)
                    }
                }
            }
        }


        view.exThreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.view.exThreeDayText
                val dotView = container.view.exThreeDotView

                textView.text = day.date.dayOfMonth.toString()

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
                            Log.d("TTTT", events.toString())
                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }

            }
        }

        view.exThreeCalendar.monthScrollListener = {
            if (exThreeCalendar.maxRowCount == 6) {
                titleFormatter.format(it.yearMonth)
                // Select the first day of the month when
                // we scroll to a new month.
                selectDate(it.yearMonth.atDay(1))
            } else {
                // In week mode, we show the header a bit differently.
                // We show indices with dates from different months since
                // dates overflow and cells in one index can belong to different
                // months/years.
                val firstDate = it.weekDays.first().first().date
                val lastDate = it.weekDays.last().last().date
                if (firstDate.yearMonth == lastDate.yearMonth) {
                    exThreeSelectedDateText.text = firstDate.yearMonth.year.toString()
                } else {

                    if (firstDate.year == lastDate.year) {
                        exThreeSelectedDateText.text = firstDate.yearMonth.year.toString()
                    } else {
                        exThreeSelectedDateText.text = "${firstDate.yearMonth.year} - ${lastDate.yearMonth.year}"
                    }
                }
            }



        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = view.findViewById<LinearLayout>(R.id.legendLayout)
        }
        view.exThreeCalendar.monthHeaderBinder = object :
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

        view.exThreeAddButton.setOnClickListener {
            var intent = Intent(activity, ScheduleInputActivity::class.java)
            startActivity(intent)
        }



        view.weekModeCheckBox.setOnCheckedChangeListener { _, monthToWeek ->
            val firstDate = exThreeCalendar.findFirstVisibleDay()?.date ?: return@setOnCheckedChangeListener
            val lastDate = exThreeCalendar.findLastVisibleDay()?.date ?: return@setOnCheckedChangeListener

            val oneWeekHeight = exThreeCalendar.daySize.height
            val oneMonthHeight = oneWeekHeight * 6

            val oldHeight = if (monthToWeek) oneMonthHeight else oneWeekHeight
            val newHeight = if (monthToWeek) oneWeekHeight else oneMonthHeight

            // Animate calendar height changes.
            val animator = ValueAnimator.ofInt(oldHeight, newHeight)
            animator.addUpdateListener { animator ->
                exThreeCalendar.updateLayoutParams {
                    height = animator.animatedValue as Int
                }
            }

            // When changing from month to week mode, we change the calendar's
            // config at the end of the animation(doOnEnd) but when changing
            // from week to month mode, we change the calendar's config at
            // the start of the animation(doOnStart). This is so that the change
            // in height is visible. You can do this whichever way you prefer.

            animator.doOnStart {
                if (!monthToWeek) {
                    exThreeCalendar.updateMonthConfiguration(
                        inDateStyle = InDateStyle.ALL_MONTHS,
                        maxRowCount = 6,
                        hasBoundaries = true
                    )
                }
            }
            animator.doOnEnd {
                if (monthToWeek) {
                    exThreeCalendar.updateMonthConfiguration(
                        inDateStyle = InDateStyle.FIRST_MONTH,
                        maxRowCount = 1,
                        hasBoundaries = false
                    )
                }

                if (monthToWeek) {
                    // We want the first visible day to remain
                    // visible when we change to week mode.
                    exThreeCalendar.scrollToDate(firstDate)
                } else {
                    // When changing to month mode, we choose current
                    // month if it is the only one in the current frame.
                    // if we have multiple months in one frame, we prefer
                    // the second one unless it's an outDate in the last index.
                    if (firstDate.yearMonth == lastDate.yearMonth) {
                        exThreeCalendar.scrollToMonth(firstDate.yearMonth)
                    } else {
                        // We compare the next with the last month on the calendar so we don't go over.
                        exThreeCalendar.scrollToMonth(minOf(firstDate.yearMonth.next, currentMonth.plusMonths(10)))
                    }
                }
            }
            animator.duration = 250
            animator.start()
        }







        return view
    }


    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        calendarInfoDTO = CalendarInfoDTO()
    }

    fun dpToPx(dp: Int, context: Context): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()



    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { exThreeCalendar.notifyDateChanged(it) }
            exThreeCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun saveEvent() {

        var now = LocalDate.now()
        var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))


        firebaseFirestore
            ?.collection("CalendarInfo")?.document(userId!!)?.collection(Strnow)
                    ?.get(Source.CACHE)?.addOnSuccessListener { querySnapshot ->

                        events.clear()
                        if (querySnapshot == null) return@addOnSuccessListener

                        for (snapshot in querySnapshot!!) {
                            var x = LocalDate.parse(snapshot["date"].toString(), DateTimeFormatter.ISO_DATE)
                            Log.d("TTTT", snapshot["dest"].toString())
                            Log.d("TTTT", snapshot["date"].toString())


                            events[x] = events[x].orEmpty().plus(Event(
                                snapshot["dest"].toString(),
                                snapshot["date"].toString(),
                                snapshot["start"].toString().toInt() ,
                                snapshot["start_t"].toString().toLong() ,
                                snapshot["end"].toString().toInt() ,
                                snapshot["end_t"].toString().toLong()
                            ))

                            updateAdapterForDate(x)
                        }
                    }






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
        exThreeSelectedDateText.text = selectionFormatter.format(date)
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

    fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)


    fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))

    fun View.makeVisible() {
        visibility = View.VISIBLE
    }

    fun View.makeInVisible() {
        visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()

        saveEvent()
    }

}

class Example3EventsAdapter(val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var events = mutableListOf<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.example_3_event_item_view, parent, false)

        return CustomViewHolder(view)
    }

    inner class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var view = (viewHolder as CustomViewHolder).itemView

        view.itemEventText.text = events[position].dest
        view.setOnClickListener { onClick(events[position]) }

    }

    override fun getItemCount(): Int = events.size


}





