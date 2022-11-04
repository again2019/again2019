package com.goingbacking.goingbacking.util

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.goingbacking.goingbacking.Model.UserInfoDTO
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

// 여러 가지 시간 function
fun dd() : String {
    val now = LocalDate.now()
    val strnow = now.format(DateTimeFormatter.ofPattern("dd"))
    return strnow
}

fun mm() : String {
    val now = LocalDate.now()
    val strnow = now.format(DateTimeFormatter.ofPattern("MM"))
    return strnow
}

fun yyyy() : String {
    val now = LocalDate.now()
    val strnow = now.format(DateTimeFormatter.ofPattern("yyyy"))
    return strnow
}

fun yyyymm() : String {
    val now = LocalDate.now()
    val strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))
    return strnow
}

// 하는 중
fun yyyymmdd(new : LocalDate?) : String {
    if (new == null) {
        val now = LocalDate.now()
        val strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return strnow
    } else {
        val strnow = new.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return strnow
    }

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