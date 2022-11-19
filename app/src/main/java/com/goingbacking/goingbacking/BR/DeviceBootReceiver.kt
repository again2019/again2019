package com.goingbacking.goingbacking.BR

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.goingbacking.goingbacking.AppConstants
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.Repository.Alarm.AlarmRepository
import com.goingbacking.goingbacking.UI.Main.MainActivity
import com.goingbacking.goingbacking.util.*
import java.util.*

class DeviceBootReceiver : BroadcastReceiver() {
    private var todayTotalTime = 0
    private lateinit var calendar: Calendar

    private val alarmRepository = AlarmRepository()
    lateinit var notificationManager: NotificationManager
    private var whatToDoArraList = ArrayList<String>()
    private var whatToDoTimeArrayList = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals("android.intent.action.BOOT_COMPLETED")) {
            toast(context, "오늘 일정을 업데이트 하는 중입니다.")

            val alarmIntent = Intent(context, CountReceiver::class.java)
            val pendingIntent :PendingIntent = PendingIntent.getBroadcast(context, 0,
                alarmIntent, FLAG_MUTABLE)

            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if(currentday("yyyy-MM-dd").equals(PrefUtil.getRecentDate(context))) {
                getTodayInfo(context, intent)
            } else {
                val calendar2 = Calendar.getInstance()
                calendar2.timeInMillis = System.currentTimeMillis()
                calendar2.add(Calendar.MINUTE, 1)

                manager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar2.getTimeInMillis(),
                    pendingIntent
                )
            }




        }
    }


    // 당일 통근/통학 시간, 일정, 일정 시간에 대해서 데이터를 받아오는 코드 -> sharedPreference에 저장
    @RequiresApi(Build.VERSION_CODES.S)
    private fun getTodayInfo(context: Context, intent: Intent) {

        alarmRepository.getTodayInfo {
            //Log.d("experiment", "$it" )
            if (it.size == 0) {
                toast(context, context.getString(R.string.no_schedule))
                // 당일 일정에 대한 통근/통학 시간을 초가화함.
                // 만약에 통근/통학 시간에 대한 일정이 없다면 0으로 저장
                PrefUtil.setTodayTotalTime(0,context)
                PrefUtil.setTodayWhatToDo(whatToDoArraList.toString(), context)
                PrefUtil.setTodayWhatToDoTime(whatToDoTimeArrayList.toString(), context)
            } else {
                // 만약에 통근/통학 시간에 대한 일정이 있다면
                var beforeInfo = Event()

                for (IdCount in it.indices) {
                    beforefireReminder(context, intent, IdCount+1, beforeInfo, it.get(IdCount))
                    //Log.d("experiment", ": $IdCount, $beforeInfo, ${it.get(IdCount)}" )
                    beforeInfo = it.get(IdCount)
                }

                beforefireReminder(context, intent, it.size+1, beforeInfo, beforeInfo)
                //Log.d("experiment", ": ${it.size+1}, $beforeInfo, ${beforeInfo}" )

                // 당일 시간에 대한 저장
                PrefUtil.setTodayTotalTime(todayTotalTime, context)
                // 당일 일정에 대한 저장
                PrefUtil.setTodayWhatToDo(whatToDoArraList.toString(), context)
                // 당일 일정 시간에 대한 저장
                PrefUtil.setTodayWhatToDoTime(whatToDoTimeArrayList.toString(), context)

                Log.d("experiment", "todayTotaltiem ${todayTotalTime} whatToDoArraList ${whatToDoArraList} whatToDoTimeArrayList ${whatToDoTimeArrayList}")
                notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                createNotificationChannel(intent, notificationManager)
                fireReminder(context, intent, notificationManager)
            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun beforefireReminder(context: Context, intent: Intent, IdCount: Int, beforeInfoDTO: Event, nowInfoDTO: Event) {
        val id = IdCount
        val type = intent.getStringExtra(Constants.TYPE) + "wakeUpAlarm ${id}"
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, DoingReceiver::class.java)
        nextIntent.putExtra(Constants.ID, id)
        nextIntent.putExtra(Constants.TYPE, type)
        nextIntent.action = AppConstants.ACTION_READY


        if (beforeInfoDTO.date == null) {
            todayTotalTime = todayTotalTime + nowInfoDTO.start_t!!.toInt()

            whatToDoArraList.add(Constants.MOVETIME)
            whatToDoTimeArrayList.add("${nowInfoDTO.start!!-nowInfoDTO.start_t!!.toInt()}-${nowInfoDTO.start!!}")
            whatToDoArraList.add(nowInfoDTO.dest.toString())
            whatToDoTimeArrayList.add("${nowInfoDTO.start!!}-${nowInfoDTO.end!!}")

            //Log.d("experiment", "todayTotal ${nowInfoDTO.start_t!!.toInt()} = ${todayTotalTime}")
            nextIntent.putExtra(Constants.END_TIME, nowInfoDTO.start!!)

            calendar = calendarAlarm(
                hour = (nowInfoDTO.start!! - nowInfoDTO.start_t!!) / 60,
                minute = (nowInfoDTO.start!! - nowInfoDTO.start_t!!) % 60,
                second = 0,
                millisecond = 0
            )

            val stateCalendar = calendarAlarm(
                hour = nowInfoDTO.start!! / 60,
                minute = nowInfoDTO.start!! % 60,
                second = 0,
                millisecond = 0
            )

            if (System.currentTimeMillis() > stateCalendar.timeInMillis) {
                return
            }

        } else if (beforeInfoDTO.equals(nowInfoDTO)) {
            todayTotalTime = todayTotalTime + beforeInfoDTO.end_t!!.toInt()

            whatToDoArraList.add(Constants.MOVETIME)
            whatToDoTimeArrayList.add("${beforeInfoDTO.end!!}-${beforeInfoDTO.end!!+beforeInfoDTO.end_t!!}")

            //Log.d("experiment", "todayTotal ${nowInfoDTO.end_t!!.toInt()} = ${todayTotalTime}")

            nextIntent.putExtra(Constants.END_TIME, nowInfoDTO.end!! + nowInfoDTO.end_t!!)

            calendar = calendarAlarm(
                hour = (nowInfoDTO.end!!) / 60,
                minute = (nowInfoDTO.end!!) % 60,
                second = 0,
                millisecond = 0
            )


//            val stateCalendar = calendarAlarm(
//                hour = (nowInfoDTO.end!!  + nowInfoDTO.end_t!!) / 60,
//                minute = (nowInfoDTO.end!!  + nowInfoDTO.end_t!!)  % 60,
//                second = 0,
//                millisecond = 0
//            )
//
//            if (System.currentTimeMillis() > stateCalendar.timeInMillis) {
//                return
//            }
        }

        else {

            todayTotalTime = todayTotalTime + (nowInfoDTO.start!!.toInt() - beforeInfoDTO.end!!.toInt())
            whatToDoArraList.add(Constants.MOVETIME)
            whatToDoTimeArrayList.add("${beforeInfoDTO.end!!}-${nowInfoDTO.start!!}")
            whatToDoArraList.add(nowInfoDTO.dest.toString())
            whatToDoTimeArrayList.add("${nowInfoDTO.start!!}-${nowInfoDTO.start!!+nowInfoDTO.start_t!!}")

            //Log.d("experiment", "todayTotal ${(nowInfoDTO.start!!.toInt() - beforeInfoDTO.end!!.toInt())} = ${todayTotalTime}")
            nextIntent.putExtra(Constants.END_TIME, nowInfoDTO.start!!)
            calendar = calendarAlarm(
                hour = (beforeInfoDTO.end!! / 60),
                minute = (beforeInfoDTO.end!! % 60),
                second = 0,
                millisecond = 0
            )

//            val stateCalendar = calendarAlarm(
//                hour = (nowInfoDTO.start!!) / 60,
//                minute = (nowInfoDTO.start!!)  % 60,
//                second = 0,
//                millisecond = 0
//            )
//
//            if (System.currentTimeMillis() > stateCalendar.timeInMillis) {
//                return
//            }
        }

        nextIntent.putExtra(Constants.ID, id)
        nextIntent.putExtra(Constants.TYPE, type)
        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, FLAG_MUTABLE)


        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)

    }


    private fun createNotificationChannel(intent:Intent, notificationManager: NotificationManager) {
        val id = intent.getIntExtra(Constants.ID, 0)
        val type = intent.getStringExtra(Constants.TYPE)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("notificationChannel_$id", "$type", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.run{
                enableVibration(true)
                description = "notification"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun fireReminder(context: Context, intent: Intent, notificationManager: NotificationManager) {
        val id = intent.getIntExtra(Constants.ID, 0)
        val type = intent.getStringExtra(Constants.TYPE)

        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(context, id, contentIntent, FLAG_MUTABLE)
        val builder = NotificationCompat.Builder(context, "notificationChannel_$id")
            .setSmallIcon(R.mipmap.com_back_new)
            .setContentTitle("오늘 일정을 업데이트 하는 중입니다.")
            .setContentText("오늘 일정을 업데이트 하는 중입니다.")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText("오늘 일정을 업데이트 하는 중입니다."))
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(id, builder.build())
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, CountReceiver::class.java)
        nextIntent.putExtra(Constants.ID, id)
        nextIntent.putExtra(Constants.TYPE, type)

        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, FLAG_MUTABLE)

        val calendar = calendar(0,0,0,0)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)


    }
}