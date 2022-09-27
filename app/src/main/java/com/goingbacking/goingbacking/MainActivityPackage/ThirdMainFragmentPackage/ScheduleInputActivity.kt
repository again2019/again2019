package com.goingbacking.goingbacking.MainActivityPackage.ThirdMainFragmentPackage

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.R
import com.applikeysolutions.cosmocalendar.model.Day
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.goingbacking.goingbacking.Model.DateDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_schedule_input.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

data class dateInfo (var year : Int? = null, var monthOfYear :Int? = null, var dayOfMonth : Int? = null)
class ScheduleInputActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null


    var destinationPlace: String? = null
    var list : MutableList<Day>? = null
    var home1time: Int? = null
    var home2time: Int? = null
    var dest1time: Int? = null
    var dest2time: Int? = null
    val cal = Calendar.getInstance()
    var yearmonthday : String? = null
    var dateDTO : DateDTO? = null

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
        setContentView(R.layout.activity_schedule_input)
        init()


        destinationPlace = destinationPlaceEditText.text.toString()

        durationCalendarView.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            if(durationCalendarView.selectedDates.size <= 0) return@OnDaySelectedListener
            list = durationCalendarView.selectedDays
            Log.d("TTTT","${durationCalendarView.selectedDays}")
        })
        home1Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                home1time = (hourOfDay * 60) + minute
                home1Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        home2Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                home2time = (hourOfDay * 60) + minute
                home2Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        dest1Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dest1time = (hourOfDay * 60) + minute
                dest1Text.text ="${hourOfDay}-${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        dest2Button.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                dest2time = (hourOfDay * 60) + minute
                dest2Text.text = "${hourOfDay}-${minute}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
        }

        if(MondayCheckBox.isChecked) {
            monday = true
            Toast.makeText(this,"MondayCheckBox.isChecked", Toast.LENGTH_SHORT ).show()
        }


        MondayCheckBox.setOnCheckedChangeListener{
            buttonView, isChecked ->
            if (isChecked) {
                monday = true
                Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
            }
        }
        TuesdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                tuesday = true
                Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
            }
        }
        WednesdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                wednesday = true
                Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
            }
        }
        ThursdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                thursday = true
                Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
            }
        }
        FridayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                friday = true
                Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
            }
        }
        SaturdayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                saturday = true
                Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
            }
        }
        SundayCheckBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
            if (isChecked) {
                sunday = true
                Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
            }
        }



        finishButton.setOnClickListener {
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
                var x = yearList.joinToString(",")
                Log.d("TTTT", x)

                dateDTO!!.date = x

                firebaseFirestore?.collection("TmpDate")?.document(userId!!)?.set(dateDTO!!)

                if(dayofweek == "2" && monday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "3" && tuesday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "4" && wednesday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "5" && thursday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "6" && friday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "7" && saturday) calendarInfoDatabaseInPut(day)
                else if (dayofweek == "1" && sunday) calendarInfoDatabaseInPut(day)


            }
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
        var event1 = Event()
        event1!!.dest = "임시"
        event1!!.date = year + "-" +  month + "-" + dayofmonth
        event1!!.start = home2time
        event1!!.end = dest1time
        event1!!.start_t = home2time!!-home1time!!
        event1!!.end_t = dest2time!! - dest1time!!


//        event1!!.start_t = convertDateToTimeStamp(year + "-" +  month + "-" + dayofmonth + "-" + home2Text.text.toString())
//        event1!!.end_t = convertDateToTimeStamp(year + "-" +  month + "-" + dayofmonth + "-" + dest1Text.text.toString())


//        var event2 = Event()
//        event2!!.dest = "move1"
//        event2!!.date = year + "-" +  month + "-" + dayofmonth
//        event2!!.start = home1time
//        event2!!.end = home2time
//        event2!!.start_t = convertDateToTimeStamp(year + "-" +  month + "-" + dayofmonth + "-" + home1Text.text.toString())
//        event2!!.end_t = convertDateToTimeStamp(year + "-" +  month + "-" + dayofmonth + "-" + home2Text.text.toString())
//
//        var event3 = Event()
//        event3!!.dest = "move2"
//        event3!!.date = year + "-" +  month + "-" + dayofmonth
//        event3!!.start = dest1time
//        event3!!.end = dest2time
//        event3!!.start_t = convertDateToTimeStamp(year + "-" +  month + "-" + dayofmonth + "-" + dest1Text.text.toString())
//        event3!!.end_t = convertDateToTimeStamp(year + "-" +  month + "-" + dayofmonth + "-" + dest2Text.text.toString())

//        firebaseFirestore?.collection("TmpCalendarInfo")?.document(userId!!)
//            ?.collection(year + "-" +  month)?.document(dayofmonth)
//            ?.collection(dayofmonth)?.document(home2time.toString()+ '-' + dest1time.toString())?.set(event1)

        firebaseFirestore?.collection("CalendarInfo")?.document(userId!!)
            ?.collection(year + "-" +  month)?.document(convertDateToTimeStamp(year + "-" +  month + "-" + dayofmonth + "-" + home2Text.text.toString()).toString())
            ?.set(event1)

    }

    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        dateDTO = DateDTO()
    }

    fun convertDateToTimeStamp(date: String) : Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm")
        return sdf.parse(date).time
    }
}