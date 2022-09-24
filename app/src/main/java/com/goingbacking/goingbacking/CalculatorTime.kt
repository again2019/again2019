package com.goingbacking.goingbacking

import java.util.*


fun getOneDayIntervalTime(): Long{

    val currentDate = Calendar.getInstance()
    val dueDate = Calendar.getInstance().apply {

        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 10)

    }

    if (dueDate.before(currentDate))
        dueDate.add(Calendar.HOUR_OF_DAY, 24)


    return dueDate.timeInMillis - currentDate.timeInMillis
}

fun getOneMinIntervalTime(): Long{

    val currentDate = Calendar.getInstance()
    val dueDate = Calendar.getInstance().apply {
        add(Calendar.MINUTE, 1)
    }

    return dueDate.timeInMillis - currentDate.timeInMillis
}