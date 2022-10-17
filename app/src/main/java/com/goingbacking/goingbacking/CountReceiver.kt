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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CountReceiver : BroadcastReceiver() {
    val alarmRepository = AlarmRepository(FirebaseAuth.getInstance().currentUser, FirebaseFirestore.getInstance())
    lateinit var notificationManager: NotificationManager
    var sharedPreferences : SharedPreferences? = null
    var exp1 :Int? = null
    var exp2 : String? = null



    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "count receiverstart", Toast.LENGTH_SHORT).show()

        saveDailyInfo()
        getTodayInfo(context, intent)



//
//        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        sharedPreferences = context.getSharedPreferences("time", AppCompatActivity.MODE_PRIVATE)
//        exp1 = sharedPreferences!!.getInt("TodayTime", 4)
//        exp2 = sharedPreferences!!.getString("TodayStrTime", ",420-630,1080-1200")
//
//        var exp2_split = exp2!!.split(',').toMutableList()
//
//        exp2_split.removeAt(0)
//
//        var exp3_1 = arrayListOf<Any>()
//        var exp3_2 = arrayListOf<Any>()
//
//        for(i in exp2_split) {
//            Log.d("experiment", i)
//            var xx1 = i.split('-').toMutableList().get(0)
//            var xx2 = i.split('-').toMutableList().get(1)
//
//            exp3_1.add(xx1)
//            exp3_2.add(xx2)
//
//        }
//
//        Log.d("experiment", exp1.toString())
//        Log.d("experiment", exp2!!)
//        Log.d("experiment", exp2_split!!.toString())
//        Log.d("experiment", exp3_1.size!!.toString())
//        Log.d("experiment", exp3_2!!.toString())
//
//
//        for (i in exp3_1.size-1 downTo 0 ) {
//            beforefireReminder(context, intent, i, i)
//            Log.d("experiment", "i: $i")
//        }
//        createNotificationChannel(intent)
//        fireReminder(context, intent)


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
                    IdCount = IdCount + 1
                    beforeInfo = document.toObject(CalendarInfoDTO::class.java)
                }

                notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            }
            ?.addOnFailureListener {

            }
    }






    }

    private fun beforefireReminder(context: Context, intent: Intent, IdCount: Int, beforeInfoDTO: CalendarInfoDTO, nowInfoDTO: CalendarInfoDTO) {
        val id = IdCount
        val type = intent.getStringExtra("type") + "wakeUpAlarm"
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, DoingReceiver::class.java)
        nextIntent.putExtra("id", id)
        nextIntent.putExtra("type", type)
        nextIntent.action = AppConstants.ACTION_READY
        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, PendingIntent.FLAG_MUTABLE)

        var calendar = Calendar.getInstance()
        if (beforeInfoDTO.date == null) {
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, (nowInfoDTO.start!! - nowInfoDTO.start_t!!) / 60)
            calendar.set(Calendar.MINUTE, (nowInfoDTO.start!! - nowInfoDTO.start_t!!) % 60)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        } else {
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, (beforeInfoDTO.end!! / 60))
            calendar.set(Calendar.MINUTE, (beforeInfoDTO.end!! % 60))
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }

        Log.d(
            "experiment",
            "just alarm SET:$id | type: $type |"
        )

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)

    }


//    private fun createNotificationChannel(intent:Intent) {
//        val id = intent.getIntExtra("id", 0)
//        val type = intent.getStringExtra("type")
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel("notificationChannel_$id", "$type", NotificationManager.IMPORTANCE_HIGH)
//            notificationChannel.run{
//                enableVibration(true)
//                description = "notification"
//            }
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//    }
//
//    private fun fireReminder(context: Context, intent: Intent) {
//        val id = intent.getIntExtra("id", 0)
//        val type = intent.getStringExtra("type")
//        val isRepeat = intent.getBooleanExtra("repeat", false)
//        val dateTime = try {
//            intent.getSerializableExtra("time") as LocalDateTime
//        } catch(e: NullPointerException) {
//            LocalDateTime.now()
//        }
//
//        val contentIntent = Intent(context, MainActivity::class.java)
//        val contentPendingIntent = PendingIntent.getActivity(context, id, contentIntent, PendingIntent.FLAG_MUTABLE)
//        val builder = NotificationCompat.Builder(context, "notificationChannel_$id")
//            .setSmallIcon(R.mipmap.comeback)
//            .setContentTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//            .setContentText("aaaaaaaaaaaaaaaaaa")
//            .setContentIntent(contentPendingIntent)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//            .setStyle(NotificationCompat.BigTextStyle().bigText("aaaaaaaaaaaaaaaaaa"))
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
//
//        notificationManager.notify(id, builder.build())
//        if(!isRepeat) return
//        val interval  = intent.getIntExtra("interval", 1)
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val nextIntent = Intent(context, CountReceiver::class.java)
//        nextIntent.putExtra("id", id)
//        nextIntent.putExtra("type", type)
//        nextIntent.putExtra("repeat", true)
//        nextIntent.putExtra("interval", interval)
//
//        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, PendingIntent.FLAG_MUTABLE)
//        //val nextDate = dateTime.plusDays(interval.toLong())
//        val nextDate = dateTime.plusSeconds(interval.toLong())
//        Log.d(
//            "experiment",
//        "recursive SET:$id | type: $type |time:$nextDate |interval $interval| isRepeat:true|"
//        )
//
//        Log.d("experiment", nextDate.hour.toString() + nextDate.minute.toString() + nextDate.second.toString())
//        var calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.set(Calendar.HOUR_OF_DAY, nextDate.hour)
//        calendar.set(Calendar.MINUTE, nextDate.minute)
//        calendar.set(Calendar.SECOND, nextDate.second)
//        calendar.set(Calendar.MILLISECOND, 0)
//
//        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis, pendingIntent)
//
//
//    }
//
//}