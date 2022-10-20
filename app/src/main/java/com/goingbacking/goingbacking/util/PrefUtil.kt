package com.goingbacking.goingbacking.util

import android.content.Context
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

        private const val ALARM_SET_Total_Today_TIME_ID = "TodayTotalTime"


        fun setTodayTotalTime(time: Int, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt(ALARM_SET_Total_Today_TIME_ID, time)
            editor.apply()
        }

        fun getTodayTotalTime(context:Context) : Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getInt(ALARM_SET_Total_Today_TIME_ID, 0)

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

        private const val ALARM_SET_TODAY_WHAT_TO_DO_ID = "today_what_to_do"

        fun getTodayWhatToDo(context: Context): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getString(ALARM_SET_TODAY_WHAT_TO_DO_ID, "")
        }

        fun setTodayWhatToDo(str: String, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(ALARM_SET_TODAY_WHAT_TO_DO_ID, str)
            editor.apply()
        }

        private const val ALARM_SET_TODAY_WHAT_TO_DO_TIME_ID = "today_what_to_do_time"

        fun getTodayWhatToDoTime(context: Context): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getString(ALARM_SET_TODAY_WHAT_TO_DO_TIME_ID, "")
        }

        fun setTodayWhatToDoTime(str: String, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(ALARM_SET_TODAY_WHAT_TO_DO_TIME_ID, str)
            editor.apply()
        }
    }
}