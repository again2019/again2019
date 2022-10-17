package com.goingbacking.goingbacking

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.ID
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment
import com.goingbacking.goingbacking.Model.CalendarInfoDTO
import com.goingbacking.goingbacking.Model.SaveTimeDayDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.Repository.AlarmRepository
import com.goingbacking.goingbacking.ViewModel.AlarmViewModel
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CountReceiver : BroadcastReceiver() {
    val alarmRepository = AlarmRepository(FirebaseAuth.getInstance().currentUser, FirebaseFirestore.getInstance())
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "count receiverstart", Toast.LENGTH_SHORT).show()

        saveDailyInfo()
        getTodayInfo(context, intent)

    }

    private fun saveDailyInfo() {
        alarmRepository.addFirstInitSaveTimeMonthInfo {}
        alarmRepository.addFirstInitSaveTimeYearInfo {}
        alarmRepository.addInitSaveTimeDayInfo {}
    }

    private fun getTodayInfo(context: Context, intent: Intent) {
        var now = LocalDate.now()
        var Strnow1 = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        var Strnow2 = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


        FirebaseFirestore.getInstance().collection("CalendarInfo").document(FirebaseAuth.getInstance().currentUser?.uid!!)
            ?.collection(Strnow1).whereEqualTo("date", Strnow2).get()
            .addOnSuccessListener {
                var todayDTOList = arrayListOf<CalendarInfoDTO>()
                var IdCount = 1
                var beforeInfo = CalendarInfoDTO()
                for (document in it) {
                    todayDTOList.add(document.toObject(CalendarInfoDTO::class.java))
                    beforefireReminder(context, intent, IdCount, beforeInfo, document.toObject(CalendarInfoDTO::class.java))
                    Log.d("experiment", ": $IdCount, $beforeInfo, ${document.toObject(CalendarInfoDTO::class.java)}" )
                    IdCount = IdCount + 1
                    beforeInfo = document.toObject(CalendarInfoDTO::class.java)
                }
                    beforefireReminder(context, intent, IdCount, beforeInfo, beforeInfo)
                    Log.d("experiment", ": $IdCount, $beforeInfo, ${beforeInfo}" )

                notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                createNotificationChannel(intent, notificationManager)
                fireReminder(context, intent, notificationManager)

            }
            ?.addOnFailureListener {

            }
    }


    }

    private fun beforefireReminder(context: Context, intent: Intent, IdCount: Int, beforeInfoDTO: CalendarInfoDTO, nowInfoDTO: CalendarInfoDTO) {
        val id = IdCount
        val type = intent.getStringExtra("type") + "wakeUpAlarm ${id}"
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var nextIntent = Intent(context, DoingReceiver::class.java)
        nextIntent.putExtra("id", id)
        nextIntent.putExtra("type", type)
        nextIntent.action = AppConstants.ACTION_READY

        var calendar = Calendar.getInstance()
        if (beforeInfoDTO.date == null) {

            nextIntent.putExtra("end_time", nowInfoDTO.start!!)
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, (nowInfoDTO.start!! - nowInfoDTO.start_t!!) / 60)
            calendar.set(Calendar.MINUTE, (nowInfoDTO.start!! - nowInfoDTO.start_t!!) % 60)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        } else if (beforeInfoDTO.equals(CalendarInfoDTO())) {
            nextIntent.putExtra("end_time", nowInfoDTO.end!! + nowInfoDTO.end_t!!)
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, (nowInfoDTO.end!!) / 60)
            calendar.set(Calendar.MINUTE, (nowInfoDTO.end!!) % 60)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }

        else {
            nextIntent.putExtra("end_time", nowInfoDTO.start!!)
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, (beforeInfoDTO.end!! / 60))
            calendar.set(Calendar.MINUTE, (beforeInfoDTO.end!! % 60))
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }

        Log.d(
            "experiment",
            "just alarm SET:$id | type: $type | ${calendar.timeInMillis} | ${SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm").format(calendar.timeInMillis) } |"
        )
        nextIntent.putExtra("id", id)
        nextIntent.putExtra("channel", type)
        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, PendingIntent.FLAG_MUTABLE)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)

    }


    private fun createNotificationChannel(intent:Intent, notificationManager: NotificationManager) {
        val id = intent.getIntExtra("id", 0)
        val type = intent.getStringExtra("type")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("notificationChannel_$id", "$type", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.run{
                enableVibration(true)
                description = "notification"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun fireReminder(context: Context, intent: Intent, notificationManager: NotificationManager) {
        val id = intent.getIntExtra("id", 0)
        val type = intent.getStringExtra("type")

        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(context, id, contentIntent, PendingIntent.FLAG_MUTABLE)
        val builder = NotificationCompat.Builder(context, "notificationChannel_$id")
            .setSmallIcon(R.mipmap.comeback)
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
        nextIntent.putExtra("id", id)
        nextIntent.putExtra("type", type)

        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, PendingIntent.FLAG_MUTABLE)

        Log.d(
            "experiment",
        "recursive SET:$id | type: $type |"
        )

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 5)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.DATE, 1)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)


    }


