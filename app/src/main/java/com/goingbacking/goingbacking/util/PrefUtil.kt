package com.goingbacking.goingbacking.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.preference.PreferenceManager

class PrefUtil {

    companion object {


        private const val SECONDS_REMAINING_ID = "seconds_remaining"

        fun getSecondsRemaining(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }

        fun setTodayTotalTime(time: Int, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt("TodayTotalTime", time)
            editor.apply()
        }

        fun getTodayTotalTime(context:Context) : Int {
            val sharedPreferences = context.getSharedPreferences("time", MODE_PRIVATE)
            return sharedPreferences!!.getInt("TodayTotalTime", 0)

        }

        private const val ALARM_SET_END_TIME_ID = "end_time"

        fun getEndTime(context: Context): Int{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getInt(ALARM_SET_END_TIME_ID, 0)
        }

        fun setEndTime(time: Int, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt(ALARM_SET_END_TIME_ID, time)
            editor.apply()
        }

        private const val ALARM_SET_START_TIME_ID = "start_time"

        fun getStartTime(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getLong(ALARM_SET_START_TIME_ID, 0L)
        }

        fun setStartTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_START_TIME_ID, time)
            editor.apply()
        }
    }
}