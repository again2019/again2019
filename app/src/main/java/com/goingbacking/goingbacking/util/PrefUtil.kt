package com.goingbacking.goingbacking.util

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth

class PrefUtil {

    companion object {
        private const val CURRENT_UID = "current_uid"

        fun setCurrentUid(uid: String?, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(CURRENT_UID, uid)
            editor.apply()
        }
        fun getCurrentUid(context: Context): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(CURRENT_UID, "")
        }

        private const val HISTORY_WHATTODO_ID = "history_whattodo"

        fun setHistoryWhatToDo(whattodoList: MutableSet<String>, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putStringSet(firebaseUid() + HISTORY_WHATTODO_ID, whattodoList)
            editor.apply()
        }
        fun getHistoryWhatToDo(context: Context): MutableSet<String>? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getStringSet(firebaseUid() + HISTORY_WHATTODO_ID, setOf<String>())
        }

        private const val SECONDS_REMAINING_ID = "seconds_remaining"

        fun getSecondsRemaining(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(firebaseUid() + SECONDS_REMAINING_ID, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(firebaseUid() + SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }

        private const val ALARM_SET_Total_Today_TIME_ID = "TodayTotalTime"


        fun setTodayTotalTime(time: Int, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt(firebaseUid() + ALARM_SET_Total_Today_TIME_ID, time)
            editor.apply()
        }

        fun getTodayTotalTime(context:Context) : Int {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getInt(firebaseUid() + ALARM_SET_Total_Today_TIME_ID, 0)

        }

        private const val ALARM_SET_END_TIME_ID = "end_time"

        fun getEndTime(context: Context): Int{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getInt(firebaseUid() + ALARM_SET_END_TIME_ID, 0)
        }

        fun setEndTime(time: Int, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt(firebaseUid() + ALARM_SET_END_TIME_ID, time)
            editor.apply()
        }

        private const val ALARM_SET_START_TIME_ID = "start_time"

        fun getStartTime(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getLong(firebaseUid() + ALARM_SET_START_TIME_ID, 0L)
        }

        fun setStartTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(firebaseUid() + ALARM_SET_START_TIME_ID, time)
            editor.apply()
        }

        private const val ALARM_SET_TODAY_WHAT_TO_DO_ID = "today_what_to_do"

        fun getTodayWhatToDo(context: Context): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getString(firebaseUid() + ALARM_SET_TODAY_WHAT_TO_DO_ID, "")
        }

        fun setTodayWhatToDo(str: String, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(firebaseUid() + ALARM_SET_TODAY_WHAT_TO_DO_ID, str)
            editor.apply()
        }

        private const val ALARM_SET_TODAY_WHAT_TO_DO_TIME_ID = "today_what_to_do_time"

        fun getTodayWhatToDoTime(context: Context): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getString(firebaseUid() + ALARM_SET_TODAY_WHAT_TO_DO_TIME_ID, "")
        }

        fun setTodayWhatToDoTime(str: String, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(firebaseUid() + ALARM_SET_TODAY_WHAT_TO_DO_TIME_ID, str)
            editor.apply()
        }

        fun firebaseUid() : String {
            return FirebaseAuth.getInstance().currentUser?.uid.toString()
        }
    }
}