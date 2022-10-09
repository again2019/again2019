package com.goingbacking.goingbacking.MainActivityPackage.ThirdMainFragmentPackage

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.goingbacking.goingbacking.Model.Event
import com.applikeysolutions.cosmocalendar.model.Day
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.ActivityScheduleInputBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

@AndroidEntryPoint
class ScheduleInputActivity : AppCompatActivity() {
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

    private val binding: ActivityScheduleInputBinding by lazy {
        ActivityScheduleInputBinding.inflate(layoutInflater)
    }
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        DateObserver(viewModel.dateDTOs)
        DateObserver(viewModel.eventDTO)

        binding.durationCalendarView.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            if(binding.durationCalendarView.selectedDates.size <= 0) return@OnDaySelectedListener
            list = binding.durationCalendarView.selectedDays
        })
        binding.home1Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                home1time = (hourOfDay * 60) + minute
                binding.home1Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(HOUR_OF_DAY), cal.get(MINUTE),false).show()
        }

        binding.home2Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                home2time = (hourOfDay * 60) + minute
                binding.home2Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(HOUR_OF_DAY), cal.get(MINUTE),false).show()
        }

        binding.dest1Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dest1time = (hourOfDay * 60) + minute
                binding.dest1Text.text ="${hourOfDay}-${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(HOUR_OF_DAY), cal.get(MINUTE),false).show()
        }

        binding.dest2Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dest2time = (hourOfDay * 60) + minute
                binding.dest2Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(HOUR_OF_DAY), cal.get(MINUTE),false).show()
        }

        binding.MondayCheckBox.setOnCheckedChangeListener{
            buttonView, isChecked ->
            if (isChecked) {
                monday = true
            }
        }
        binding.TuesdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                tuesday = true
            }
        }
        binding.WednesdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                wednesday = true
            }
        }
        binding.ThursdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                thursday = true
            }
        }
        binding.FridayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                friday = true
            }
        }
        binding.SaturdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                saturday = true
            }
        }
        binding.SundayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                sunday = true
            }
        }


        binding.finishButton.setOnClickListener {
            for(day in list!!) {
                var dayofweek = day.calendar.get(DAY_OF_WEEK).toString()

                var year = day.calendar.get(YEAR).toString()
                var month = (day.calendar.get(MONTH) + 1).toString()
                var dayofmonth = day.calendar.get(DAY_OF_MONTH).toString()


                if (month.length == 1) {
                    month = "0" + month
                }
                if (dayofmonth.length == 1) {
                    dayofmonth = "0" + dayofmonth
                }

                yearList.add(year + '-' + month + '-' + dayofmonth)


                if(dayofweek == "2" && monday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "3" && tuesday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "4" && wednesday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "5" && thursday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "6" && friday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "7" && saturday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "1" && sunday) calendarInfoDatabaseInPut(day)

            }
            dateDTO!!.date = yearList.joinToString(",")
            viewModel.addDateInfo(dateDTO!!)
            finish()

        }

    }


    fun calendarInfoDatabaseInPut(day: Day) {
        var year = day.calendar.get(YEAR).toString()
        var month = (day.calendar.get(MONTH) + 1).toString()
        var dayofmonth = day.calendar.get(DAY_OF_MONTH).toString()


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