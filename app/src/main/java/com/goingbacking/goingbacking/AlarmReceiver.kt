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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.goingbacking.goingbacking.Model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
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
        Toast.makeText(context, "count receiverstart2222222",Toast.LENGTH_SHORT).show()
        Log.d("experiment", "okay222222")

        init()

        var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationIntent = Intent(context, MainActivity::class.java)

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        var pendingI = PendingIntent.getActivity(context, 2999, notificationIntent, PendingIntent.FLAG_MUTABLE)
        var builder :NotificationCompat.Builder = NotificationCompat.Builder(context,
            "channeljust alarm"
        )

        var channelName = "매일 알람 채널"
        var description = "ssss"
        var importance = NotificationManager.IMPORTANCE_HIGH
        var channel : NotificationChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel("channeljust alarm", channelName, importance)
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
        var date_text1_1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDateTime)

        var date_text1 = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(currentDateTime)

        var date_text2 = SimpleDateFormat("dd", Locale.getDefault()).format(currentDateTime)
        var date_text3 = SimpleDateFormat("yyyy", Locale.getDefault()).format(currentDateTime)
        var date_text4 = SimpleDateFormat("MM", Locale.getDefault()).format(currentDateTime)
        var date_text5 = SimpleDateFormat("dd", Locale.getDefault()).format(currentDateTime)


        saveTimeDayDTO!!.day = date_text5.toInt()
        saveTimeDayDTO!!.month = date_text4.toInt()
        saveTimeDayDTO!!.year = date_text3.toInt()
        saveTimeDayDTO!!.count = 0

        firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
            ?.collection("Day")?.document(date_text1.toString())
            ?.collection(date_text2.toString())?.document(userId!! + date_text2.toString())
            ?.set(saveTimeDayDTO!!)

        var time = 0
        var time_str = ""

        Log.d("AAAAAAAA", "date1: " + date_text1.toString())
        firebaseFirestore?.collection("CalendarInfo")?.document(userId!!)
            ?.collection("2022-09")?.whereEqualTo("date", "2022-09-03")?.get()
            ?.addOnSuccessListener { documents ->
                if (documents.count() == 1) {
                    for(document in documents) {
                        Log.d("AAAAAAAA", "document: " + document.toString())
                        time = time +  document["end_t"].toString().toInt() + document["start_t"].toString().toInt()
                        time_str = time_str + ',' + (document["start"].toString().toInt()-document["start_t"].toString().toInt()).toString() + '-' + document["start"].toString()
                        time_str = time_str + ',' + document["end"].toString() + '-' + (document["end"].toString().toInt() + document["end_t"].toString().toInt())

                    }
                }
                else {
                    var count = 1
                    var before = Event("", "", 0,0,0)

                    for(document in documents) {
                        Log.d("AAAAAAAA", "document: " + document.toString())

                        if (count == 1) {
                            time = time + document["start_t"].toString().toInt()
                            time_str = time_str + ',' + (document["start"].toString().toInt()-document["start_t"].toString().toInt()).toString() + '-' + document["start"].toString()

                        } else if (count == documents.count()) {
                            time = time +  (document["start"].toString().toInt()- before.end!!) + (document["end_t"].toString().toInt())
                            time_str = time_str + ',' + before.end!!.toString() + '-' + document["start"].toString() + ',' + document["end"].toString() + '-' + (document["end"].toString().toInt() + document["end_t"].toString().toInt()).toString()

                        } else {
                            time = time +  (document["start"].toString().toInt()- before.end!!)
                            time_str = time_str + ',' + before.end!!.toString() + '-' + document["start"].toString()

                        }



                        count = count + 1
                        before = Event(
                            document["dest"].toString(),
                            document["date"].toString(),
                            document["start"].toString().toInt(),
                            document["start_t"].toString().toInt(),
                            document["end"].toString().toInt(),
                            document["end_t"].toString().toInt()
                        )
                    }


                }

                Log.d("AAAAAAAA", "time: " + time.toString())
                Log.d("AAAAAAAA", "time_str: " +  time_str)
                val editor :SharedPreferences.Editor = context.getSharedPreferences("time", AppCompatActivity.MODE_PRIVATE).edit()
                editor.putInt("TodayTime", time )
                editor.putString("TodayStrTime", time_str)
                editor.apply()



            }



        //Log.d("AAAAAAAAAA", stateQuery.toString())

        // 달이 바뀔 때마다 업데이트 되는 것이 필요...

        var nextNotifyTime1 = Calendar.getInstance()
        nextNotifyTime1.add(Calendar.DATE, -1)

        var currentDateTime1 = nextNotifyTime1.time
        var date_text_new1 = SimpleDateFormat("MM", Locale.getDefault()).format(currentDateTime1)

        Log.d("TTTT", date_text_new1.toString() + " " + date_text4.toString())
        if (date_text_new1 != date_text4) {
            saveTimeMonthDTO!!.month = date_text4.toInt()
            saveTimeMonthDTO!!.year = date_text3.toInt()
            saveTimeMonthDTO!!.count = 0

            firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
                ?.collection("Month")?.document(date_text3.toString())
                ?.collection(date_text2.toString())?.document(userId!! + date_text2.toString())
                ?.set(saveTimeMonthDTO!!)
        }

        // 년이 바뀔 때마다 업데이트 되는 것이 필요...

        var nextNotifyTime2 = Calendar.getInstance()
        nextNotifyTime2.add(Calendar.DATE, -1)

        var currentDateTime2 = nextNotifyTime1.time
        var date_text_new2 = SimpleDateFormat("yyyy", Locale.getDefault()).format(currentDateTime2)

        Log.d("TTTT", date_text_new2.toString() + " " + date_text3.toString())


        if (date_text_new2 != date_text2) {


            saveTimeYearDTO!!.year = date_text3.toInt()
            saveTimeYearDTO!!.count = 0

            firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
                ?.collection("Year")?.document(date_text3.toString())
                ?.set(saveTimeYearDTO!!)

        }

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