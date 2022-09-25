package com.goingbacking.goingbacking

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.goingbacking.goingbacking.Model.SaveTimeDayDTO
import com.goingbacking.goingbacking.Model.SaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.SaveTimeYearDTO
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var saveTimeDayDTO : SaveTimeDayDTO? = null
    var saveTimeMonthDTO : SaveTimeMonthDTO? = null
    var saveTimeYearDTO : SaveTimeYearDTO? = null


    override fun onReceive(context: Context, intent: Intent) {
        init()

        var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationIntent = Intent(context, MainActivity::class.java)

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        var pendingI = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
        var builder :NotificationCompat.Builder = NotificationCompat.Builder(context, "default")

        var channelName = "매일 알람 채널"
        var description = "ssss"
        var importance = NotificationManager.IMPORTANCE_HIGH
        var channel : NotificationChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel("default", channelName, importance)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        channel.description = description

        notificationManager.createNotificationChannel(channel)

        builder.setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())

            .setTicker("{Time to watch some cool stuff!}")
            .setContentTitle("상태바 드래그시 보이는 타이틀")
            .setContentText("상태바 드래그시 보이는 서브타이틀")
            .setContentInfo("INFO")
            .setContentIntent(pendingI)
            .setSmallIcon(R.drawable.ic_launcher_background)

        notificationManager.notify(1234, builder.build())
        var nextNotifyTime = Calendar.getInstance()

        nextNotifyTime.add(Calendar.DATE, 1)
        var editor :SharedPreferences.Editor = context.getSharedPreferences("daily alarm", Context.MODE_PRIVATE).edit()
        editor.putLong("nextNotifyTime", nextNotifyTime.timeInMillis)
        editor.apply()

        var currentDateTime = nextNotifyTime.time

        var date_text1 = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(currentDateTime)
        Toast.makeText(context, date_text1.toString(), Toast.LENGTH_SHORT).show()

        var date_text2 = SimpleDateFormat("dd", Locale.getDefault()).format(currentDateTime)
        var date_text3 = SimpleDateFormat("yyyy", Locale.getDefault()).format(currentDateTime)
        var date_text4 = SimpleDateFormat("MM", Locale.getDefault()).format(currentDateTime)
        var date_text5 = SimpleDateFormat("dd", Locale.getDefault()).format(currentDateTime)

        saveTimeDayDTO!!.day = date_text5.toInt()
        saveTimeDayDTO!!.month = date_text4.toInt()
        saveTimeDayDTO!!.year = date_text3.toInt()
        saveTimeDayDTO!!.count = 0

        // 달이 바뀔 때마다 업데이트 되는 것이 필요...
        firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
                ?.collection("Day")?.document(date_text1.toString())
                ?.collection(date_text2.toString())?.document(userId!! + date_text2.toString())
                ?.set(saveTimeDayDTO!!)

        saveTimeMonthDTO!!.month = date_text4.toInt()
        saveTimeMonthDTO!!.year = date_text3.toInt()
        saveTimeMonthDTO!!.count = 0

        // 년이 바뀔 때마다 업데이트 되는 것이 필요...
        firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
            ?.collection("Month")?.document(date_text3.toString())
            ?.collection(date_text2.toString())?.document(userId!! + date_text2.toString())
            ?.set(saveTimeMonthDTO!!)

        saveTimeYearDTO!!.year = date_text3.toInt()
        saveTimeYearDTO!!.count = 0

        firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
            ?.collection("Year")?.document(date_text3.toString())
            ?.set(saveTimeYearDTO!!)



    }

    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        saveTimeDayDTO = SaveTimeDayDTO()
        saveTimeMonthDTO = SaveTimeMonthDTO()
        saveTimeYearDTO = SaveTimeYearDTO()

    }
}