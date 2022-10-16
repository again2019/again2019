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
import androidx.lifecycle.observe
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment
import com.goingbacking.goingbacking.Repository.AlarmRepository
import com.goingbacking.goingbacking.ViewModel.AlarmViewModel
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.lang.NullPointerException
import java.time.LocalDateTime
import java.util.*

class CountReceiver : BroadcastReceiver() {
    lateinit var notificationManager: NotificationManager
    var sharedPreferences : SharedPreferences? = null
    var exp1 :Int? = null
    var exp2 : String? = null


    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "count receiverstart",Toast.LENGTH_SHORT).show()

        saveDailyInfo()

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
        val alarmRepository = AlarmRepository(FirebaseAuth.getInstance().currentUser, FirebaseFirestore.getInstance())
        alarmRepository.addFirstInitSaveTimeMonthInfo {}
        alarmRepository.addFirstInitSaveTimeYearInfo {}
        alarmRepository.addInitSaveTimeDayInfo {}
    }

    private fun beforefireReminder(context: Context, intent: Intent, iii: Int, idid: Int) {
        val id = intent.getIntExtra("id", 0) - 1 - idid
        val type = intent.getStringExtra("type") + "just alarm"
        val isRepeat = intent.getBooleanExtra("repeat", false)
        val dateTime = try {
            intent.getSerializableExtra("time") as LocalDateTime
        } catch(e: NullPointerException) {
            LocalDateTime.now()
        }

        if(!isRepeat) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, DoingReceiver::class.java)
        nextIntent.putExtra("id", id)
        nextIntent.putExtra("type", type)
        nextIntent.action = AppConstants.ACTION_READY
        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, PendingIntent.FLAG_MUTABLE)
        //val nextDate = dateTime.plusDays(interval.toLong())
        val interval  = intent.getIntExtra("interval", 1) -(10 * (iii+1))
        Log.d("experiment", "interval" + interval.toString())
        val nextDate = dateTime.plusSeconds(interval.toLong())


        Log.d(
            "experiment",
            "just alarm SET:$id | type: $type |time:$nextDate |interval $interval| isRepeat:true|"
        )

        Log.d("experiment", nextDate.hour.toString() + nextDate.minute.toString() + nextDate.second.toString())
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, nextDate.hour)
        calendar.set(Calendar.MINUTE, nextDate.minute)
        calendar.set(Calendar.SECOND, nextDate.second)
        calendar.set(Calendar.MILLISECOND, 0)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)

    }


    private fun createNotificationChannel(intent:Intent) {
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

    private fun fireReminder(context: Context, intent: Intent) {
        val id = intent.getIntExtra("id", 0)
        val type = intent.getStringExtra("type")
        val isRepeat = intent.getBooleanExtra("repeat", false)
        val dateTime = try {
            intent.getSerializableExtra("time") as LocalDateTime
        } catch(e: NullPointerException) {
            LocalDateTime.now()
        }

        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(context, id, contentIntent, PendingIntent.FLAG_MUTABLE)
        val builder = NotificationCompat.Builder(context, "notificationChannel_$id")
            .setSmallIcon(R.mipmap.comeback)
            .setContentTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            .setContentText("aaaaaaaaaaaaaaaaaa")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText("aaaaaaaaaaaaaaaaaa"))
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(id, builder.build())
        if(!isRepeat) return
        val interval  = intent.getIntExtra("interval", 1)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, CountReceiver::class.java)
        nextIntent.putExtra("id", id)
        nextIntent.putExtra("type", type)
        nextIntent.putExtra("repeat", true)
        nextIntent.putExtra("interval", interval)

        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, PendingIntent.FLAG_MUTABLE)
        //val nextDate = dateTime.plusDays(interval.toLong())
        val nextDate = dateTime.plusSeconds(interval.toLong())
        Log.d(
            "experiment",
        "recursive SET:$id | type: $type |time:$nextDate |interval $interval| isRepeat:true|"
        )

        Log.d("experiment", nextDate.hour.toString() + nextDate.minute.toString() + nextDate.second.toString())
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, nextDate.hour)
        calendar.set(Calendar.MINUTE, nextDate.minute)
        calendar.set(Calendar.SECOND, nextDate.second)
        calendar.set(Calendar.MILLISECOND, 0)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)


    }




}