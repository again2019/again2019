package com.goingbacking.goingbacking.util

import android.annotation.TargetApi
import android.app.*
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.goingbacking.goingbacking.AppConstants
import com.goingbacking.goingbacking.BR.DoingReceiver
import com.goingbacking.goingbacking.UI.Main.MainActivity
import com.goingbacking.goingbacking.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationUtil {
    companion object {
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "Timer App Timer"
        private const val TIMER_ID = 0

        @RequiresApi(Build.VERSION_CODES.S)
        fun showTimerExpiredNotification(context: Context) : Notification{
            val startIntent = Intent(context, DoingReceiver::class.java)
            startIntent.action = "MOVE"
            val startPendingIntent = PendingIntent.getBroadcast(context,
                0, startIntent, FLAG_MUTABLE)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer Expired!")
                .setContentText("저장하러 가실래요?")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .addAction(R.mipmap.com_back_new, "저장하러 가기", startPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            return nBuilder.build()
        }

        @RequiresApi(Build.VERSION_CODES.S)
        fun showTimerReadyNotification(context: Context) :Notification {
            val readyIntent1 = Intent(context, DoingReceiver::class.java)
            readyIntent1.action = AppConstants.ACTION_START
            val readyPendingIntent1 = PendingIntent.getBroadcast(context,
                0, readyIntent1, FLAG_MUTABLE)

            val readyIntent2 = Intent(context, DoingReceiver::class.java)
            readyIntent2.action = AppConstants.ACTION_THIS_NO_START
            val readyPendingIntent2 = PendingIntent.getBroadcast(context,
                0, readyIntent2, FLAG_MUTABLE)
            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("시작?")
                .setContentText("시작?")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .setOngoing(true)
                .addAction(R.mipmap.com_back_new, "Start?", readyPendingIntent1)
                .addAction(R.mipmap.com_back_new, "지금 안하기", readyPendingIntent2)


            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            return nBuilder.build()
        }

        @RequiresApi(Build.VERSION_CODES.S)
        fun createNotification (
            context: Context,
            wakeUpTime: Long,
            duration : Long,
        ) : Notification  {
        // 알림창에 발생하는 stop 버튼의 이벤트
            val stopIntent = Intent(context, DoingReceiver::class.java)
            stopIntent.action = AppConstants.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(context,
                0, stopIntent, FLAG_MUTABLE)
            // 알림창에 발생하는 pause 버튼의 이벤트

            val df = SimpleDateFormat("aa kk:mm:ss", Locale("ko", "KR"))
            val seconds = TimeUnit.MILLISECONDS.toSeconds(duration).toInt()
            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer is Running.")
                .setContentText("End: ${df.format(Date(wakeUpTime))}")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .setOngoing(true)
                .addAction(R.mipmap.com_back_new, "Stop", stopPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)






            return nBuilder.build()

        }

        @RequiresApi(Build.VERSION_CODES.S)
        private fun getBasicNotificationBuilder(context: Context, channelId: String, playSound: Boolean)
                : NotificationCompat.Builder{
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.com_back_new)
                .setAutoCancel(true)
                .setDefaults(0)
            if (playSound) nBuilder.setSound(notificationSound)

            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent{
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0, FLAG_MUTABLE)
        }

        @TargetApi(26)
        private fun NotificationManager.createNotificationChannel(channelID: String,
                                                                  channelName: String,
                                                                  playSound: Boolean){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW
                val nChannel = NotificationChannel(channelID, channelName, channelImportance)
                nChannel.enableLights(true)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }
        }
    }
}