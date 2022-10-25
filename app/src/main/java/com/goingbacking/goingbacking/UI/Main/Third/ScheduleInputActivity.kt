package com.goingbacking.goingbacking.UI.Main.Third

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import com.applikeysolutions.cosmocalendar.model.Day
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.ActivityScheduleInputBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ScheduleInputActivity : BaseActivity<ActivityScheduleInputBinding>({
    ActivityScheduleInputBinding.inflate(it)
}) {
    private val viewModel : MainViewModel by viewModels()

    var list : MutableList<Day>? = null
    var home1time: Int? = null
    var home2time: Int? = null
    var dest1time: Int? = null
    var dest2time: Int? = null
    val cal = Calendar.getInstance()
    var yearmonthday : String? = null
    var dateDTO : DateDTO? = DateDTO()

    var monday : Boolean = false
    var tuesday : Boolean = false
    var wednesday : Boolean = false
    var thursday : Boolean = false
    var friday : Boolean = false
    var saturday : Boolean = false
    var sunday : Boolean = false

    private var yearList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.durationCalendarView.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            if(binding.durationCalendarView.selectedDates.size <= 0) return@OnDaySelectedListener
            list = binding.durationCalendarView.selectedDays
        })

        DateObserver(viewModel.dateDTOs)
        DateObserver(viewModel.eventDTO)

        onClick()
    }

    private fun onClick() = with(binding) {
        home1Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                home1time = (hourOfDay * 60) + minute
                binding.home1Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this@ScheduleInputActivity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }
        home2Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                home2time = (hourOfDay * 60) + minute
                binding.home2Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this@ScheduleInputActivity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }
        dest1Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dest1time = (hourOfDay * 60) + minute
                binding.dest1Text.text ="${hourOfDay}-${minute}"
            }
            TimePickerDialog(this@ScheduleInputActivity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }
        dest2Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dest2time = (hourOfDay * 60) + minute
                binding.dest2Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this@ScheduleInputActivity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }
        MondayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                monday = true
            }
        }
        TuesdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                tuesday = true
            }
        }
        WednesdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                wednesday = true
            }
        }
        ThursdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                thursday = true
            }
        }
        FridayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                friday = true
            }
        }
        SaturdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                saturday = true
            }
        }
        SundayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                sunday = true
            }
        }
        finishButton.setOnClickListener {
            for(day in list!!) {
                var dayofweek = day.calendar.get(Calendar.DAY_OF_WEEK).toString()

                var year = day.calendar.get(Calendar.YEAR).toString()
                var month = (day.calendar.get(Calendar.MONTH) + 1).toString()
                var dayofmonth = day.calendar.get(Calendar.DAY_OF_MONTH).toString()


                if (month.length == 1) {
                    month = "0" + month
                }
                if (dayofmonth.length == 1) {
                    dayofmonth = "0" + dayofmonth
                }




                if(dayofweek == "2" && monday) {
                    calendarInfoDatabaseInPut(day)
                    yearList.add(year + '-' + month + '-' + dayofmonth)
                }
                else if (dayofweek == "3" && tuesday) {
                    calendarInfoDatabaseInPut(day)
                    yearList.add(year + '-' + month + '-' + dayofmonth)
                }
                else if (dayofweek == "4" && wednesday) {
                    calendarInfoDatabaseInPut(day)
                    yearList.add(year + '-' + month + '-' + dayofmonth)
                }
                else if (dayofweek == "5" && thursday) {
                    calendarInfoDatabaseInPut(day)
                    yearList.add(year + '-' + month + '-' + dayofmonth)
                }
                else if (dayofweek == "6" && friday) {
                    calendarInfoDatabaseInPut(day)
                    yearList.add(year + '-' + month + '-' + dayofmonth)
                }
                else if (dayofweek == "7" && saturday) {
                    calendarInfoDatabaseInPut(day)
                    yearList.add(year + '-' + month + '-' + dayofmonth)
                }
                else if (dayofweek == "1" && sunday) {
                    calendarInfoDatabaseInPut(day)
                    yearList.add(year + '-' + month + '-' + dayofmonth)
                }

            }
            dateDTO!!.date = yearList.joinToString(",")
            viewModel.addDateInfo(dateDTO!!)
            finish()
        }
    }

    fun calendarInfoDatabaseInPut(day: Day) {
        var year = day.calendar.get(Calendar.YEAR).toString()
        var month = (day.calendar.get(Calendar.MONTH) + 1).toString()
        var dayofmonth = day.calendar.get(Calendar.DAY_OF_MONTH).toString()


        if (month.length == 1) {
            month = "0" + month
        }
        if (dayofmonth.length == 1) {
            dayofmonth =  "0" +dayofmonth
        }



        // destination start-end
        var event = Event()
        event!!.dest = binding.destinationPlaceEditText.text.toString()
        event!!.date = year + "-" +  month + "-" + dayofmonth
        event!!.start = home2time
        event!!.end = dest1time
        event!!.start_t = home2time!!-home1time!!
        event!!.end_t = dest2time!! - dest1time!!



        var path1 = year + "-" +  month
        var path2 = convertDateToTimeStamp(year + "-" +  month + "-" + dayofmonth + "-" + binding.home2Text.text.toString()).toString()
        viewModel.addScheduleEventInfo(path1, path2, event)
    }


    fun convertDateToTimeStamp(date: String) : Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm")
        return sdf.parse(date).time
    }

    private fun DateObserver(liveData: LiveData<UiState<String>>) {
        liveData.observe(this) { state ->
            when(state) {
                is UiState.Failure -> {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}