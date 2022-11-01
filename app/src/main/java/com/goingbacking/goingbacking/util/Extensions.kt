package com.goingbacking.goingbacking.util

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

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
