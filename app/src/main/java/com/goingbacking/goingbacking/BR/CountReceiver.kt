package com.goingbacking.goingbacking.BR

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.core.app.*
import com.goingbacking.goingbacking.AppConstants
import com.goingbacking.goingbacking.UI.Main.MainActivity

import com.goingbacking.goingbacking.Model.CalendarInfoDTO
import com.goingbacking.goingbacking.R

import com.goingbacking.goingbacking.Repository.Alarm.AlarmRepository
import com.goingbacking.goingbacking.util.Constants.Companion.END_TIME
import com.goingbacking.goingbacking.util.Constants.Companion.ID
import com.goingbacking.goingbacking.util.Constants.Companion.MOVETIME
import com.goingbacking.goingbacking.util.Constants.Companion.TYPE
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.calendar
import com.goingbacking.goingbacking.util.calendarAlarm
import com.goingbacking.goingbacking.util.toast

import java.util.*

class CountReceiver : BroadcastReceiver() {
    private var todayTotalTime = 0
    private lateinit var calendar: Calendar
    val alarmRepository = AlarmRepository()
    lateinit var notificationManager: NotificationManager
    private var whatToDoArraList = ArrayList<String>()
    private var whatToDoTimeArrayList = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "count receiverstart", Toast.LENGTH_SHORT).show()

        // 매일/매달/매년 마다 새로운 날짜에 데이터베이스를 초기화함
        saveDailyInfo()
        saveWhatToDoInfo(context)
        saveRankInfo()
        getTodayInfo(context, intent)


    }

    private fun saveRankInfo() {
        alarmRepository.addInitRankInfo()
    }


    // 매일/매달/매년 마다 새로운 날짜에 데이터베이스를 초기화함
    private fun saveDailyInfo() {
        alarmRepository.addFirstInitSaveTimeMonthInfo ()
        alarmRepository.addFirstInitSaveTimeYearInfo ()
        alarmRepository.addInitSaveTimeDayInfo ()


    }

    // 매달/매년 whattodo에 대한 데이터베이스를 초기화함
    private fun saveWhatToDoInfo(context : Context) {

        val whatToDoList = PrefUtil.getHistoryWhatToDo(context)!!

        alarmRepository.addInitWhatToDoMonthInfo(whatToDoList)
        alarmRepository.addInitWhatToDoYearInfo(whatToDoList)


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
                var beforeInfo = CalendarInfoDTO()

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
    private fun beforefireReminder(context: Context, intent: Intent, IdCount: Int, beforeInfoDTO: CalendarInfoDTO, nowInfoDTO: CalendarInfoDTO) {
        val id = IdCount
        val type = intent.getStringExtra(TYPE) + "wakeUpAlarm ${id}"
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, DoingReceiver::class.java)
        nextIntent.putExtra(ID, id)
        nextIntent.putExtra(TYPE, type)
        nextIntent.action = AppConstants.ACTION_READY


        if (beforeInfoDTO.date == null) {
            todayTotalTime = todayTotalTime + nowInfoDTO.start_t!!.toInt()

            whatToDoArraList.add(MOVETIME)
            whatToDoTimeArrayList.add("${nowInfoDTO.start!!-nowInfoDTO.start_t!!.toInt()}-${nowInfoDTO.start!!}")
            whatToDoArraList.add(nowInfoDTO.dest.toString())
            whatToDoTimeArrayList.add("${nowInfoDTO.start!!}-${nowInfoDTO.end!!}")

            //Log.d("experiment", "todayTotal ${nowInfoDTO.start_t!!.toInt()} = ${todayTotalTime}")
            nextIntent.putExtra(END_TIME, nowInfoDTO.start!!)

            calendar = calendarAlarm(
                hour = (nowInfoDTO.start!! - nowInfoDTO.start_t!!) / 60,
                minute = (nowInfoDTO.start!! - nowInfoDTO.start_t!!) % 60,
                second = 0,
                millisecond = 0
            )

        } else if (beforeInfoDTO.equals(nowInfoDTO)) {
            todayTotalTime = todayTotalTime + beforeInfoDTO.end_t!!.toInt()

            whatToDoArraList.add(MOVETIME)
            whatToDoTimeArrayList.add("${beforeInfoDTO.end!!}-${beforeInfoDTO.end!!+beforeInfoDTO.end_t!!}")

            //Log.d("experiment", "todayTotal ${nowInfoDTO.end_t!!.toInt()} = ${todayTotalTime}")

            nextIntent.putExtra(END_TIME, nowInfoDTO.end!! + nowInfoDTO.end_t!!)

            calendar = calendarAlarm(
                hour = (nowInfoDTO.end!!) / 60,
                minute = (nowInfoDTO.end!!) % 60,
                second = 0,
                millisecond = 0
            )
        }

        else {

            todayTotalTime = todayTotalTime + (nowInfoDTO.start!!.toInt() - beforeInfoDTO.end!!.toInt())
            whatToDoArraList.add(MOVETIME)
            whatToDoTimeArrayList.add("${beforeInfoDTO.end!!}-${nowInfoDTO.start!!}")
            whatToDoArraList.add(nowInfoDTO.dest.toString())
            whatToDoTimeArrayList.add("${nowInfoDTO.start!!}-${nowInfoDTO.start!!+nowInfoDTO.start_t!!}")

            //Log.d("experiment", "todayTotal ${(nowInfoDTO.start!!.toInt() - beforeInfoDTO.end!!.toInt())} = ${todayTotalTime}")
            nextIntent.putExtra(END_TIME, nowInfoDTO.start!!)
            calendar = calendarAlarm(
                hour = (beforeInfoDTO.end!! / 60),
                minute = (beforeInfoDTO.end!! % 60),
                second = 0,
                millisecond = 0
            )


//            calendar.timeInMillis = System.currentTimeMillis()
//            calendar.set(Calendar.HOUR_OF_DAY, (beforeInfoDTO.end!! / 60))
//            calendar.set(Calendar.MINUTE, (beforeInfoDTO.end!! % 60))
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
        }

//        Log.d(
//            "experiment",
//            "just alarm SET:$id | type: $type | ${calendar.timeInMillis} | ${SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm").format(calendar.timeInMillis) } |"
//        )
        nextIntent.putExtra(ID, id)
        nextIntent.putExtra(TYPE, type)
        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, FLAG_MUTABLE)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)

    }


    private fun createNotificationChannel(intent:Intent, notificationManager: NotificationManager) {
        val id = intent.getIntExtra(ID, 0)
        val type = intent.getStringExtra(TYPE)

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
        val id = intent.getIntExtra(ID, 0)
        val type = intent.getStringExtra(TYPE)

        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(context, id, contentIntent, FLAG_MUTABLE)
        val builder = NotificationCompat.Builder(context, "notificationChannel_$id")
            .setSmallIcon(R.mipmap.comeback_new)
            .setContentTitle("매일마다 울리는 알림입니다")
            .setContentText("매일마다 울리는 알림입니다")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText("매일마다 울리는 알림입니다"))
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(id, builder.build())
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, CountReceiver::class.java)
        nextIntent.putExtra(ID, id)
        nextIntent.putExtra(TYPE, type)

        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, FLAG_MUTABLE)

//        Log.d(
//            "experiment",
//            "recursive SET:$id | type: $type |"
//        )

        val calendar = calendar(0,0,0,0)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)


    }


}



