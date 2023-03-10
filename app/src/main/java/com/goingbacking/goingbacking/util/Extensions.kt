package com.goingbacking.goingbacking.util

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)


fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGONE() {
    visibility = View.GONE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun toast (context: Context, string : String) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}


fun currentday(type :String) : String {
    val now = LocalDate.now()
    val strnow = now.format(DateTimeFormatter.ofPattern(type))
    return strnow
}

fun calendar (hour :Int, minute : Int, second : Int, millisecond : Int) : Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, second)
    calendar.set(Calendar.MILLISECOND, millisecond)

    if (calendar.before(Calendar.getInstance())) {
        calendar.add(Calendar.DATE, 1)
    }
    return calendar
}

fun calendarSchedule (hour :Int, minute : Int, second : Int, millisecond : Int) : Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, second)
    calendar.set(Calendar.MILLISECOND, millisecond)

    return calendar
}
fun calendarAlarm (hour :Int, minute : Int, second : Int, millisecond : Int) : Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, second)
    calendar.set(Calendar.MILLISECOND, millisecond)


    return calendar
}

fun beforeday(type :String) :String {
    val beforeNotifyTime = Calendar.getInstance()
    beforeNotifyTime.add(Calendar.DATE, -1)
    val beforeDateTime = beforeNotifyTime.time
    var bef_date_text = ""
    if (type.equals("yyyy")) {
        bef_date_text = SimpleDateFormat("yyyy", Locale.getDefault()).format(beforeDateTime).toString()
    } else if (type.equals("MM")) {
        bef_date_text = SimpleDateFormat("MM", Locale.getDefault()).format(beforeDateTime).toString()
    }
    return bef_date_text
}

fun convertDateToTimeStamp(date: String) : Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm", Locale.getDefault())
    return sdf.parse(date).time
}