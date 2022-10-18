package com.goingbacking.goingbacking.util

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.goingbacking.goingbacking.AppConstants
import com.goingbacking.goingbacking.BR.DoingReceiver
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.TmpTimeActivity
import java.text.SimpleDateFormat
import java.util.*

class NotificationUtil {
    companion object {
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_NAME_TIMER = "Timer App Timer"
        private const val TIMER_ID = 0


        fun showTimerExpiredNotification(context: Context) : Notification{
            val startIntent = Intent(context, TmpTimeActivity::class.java)
            val startPendingIntent = PendingIntent.getActivity(context,
                0, startIntent, PendingIntent.FLAG_MUTABLE)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer Expired!")
                .setContentText("저장하러 가실래요?")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .addAction(R.drawable.border_top_bottom, "저장하러 가기", startPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            return nBuilder.build()
        }


        fun showTimerReadyNotification(context: Context) :Notification {
            val readyIntent = Intent(context, DoingReceiver::class.java)
            readyIntent.action = AppConstants.ACTION_START
            val readyPendingIntent = PendingIntent.getBroadcast(context,
                0, readyIntent, PendingIntent.FLAG_MUTABLE)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("시작?")
                .setContentText("시작?")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .setOngoing(true)
                .addAction(R.drawable.btn_google_signin_dark_focus, "Start?", readyPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)

            return nBuilder.build()
        }

        fun createNotification (
            context: Context,
            wakeUpTime: Long
        ) : Notification  {
        // 알림창에 발생하는 stop 버튼의 이벤트
            val stopIntent = Intent(context, DoingReceiver::class.java)
            stopIntent.action = AppConstants.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(context,
                0, stopIntent, PendingIntent.FLAG_MUTABLE)
            // 알림창에 발생하는 pause 버튼의 이벤트

            val df = SimpleDateFormat("HH:mm:ss")

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER, true)
            nBuilder.setContentTitle("Timer is Running.")
                .setContentText("End: ${df.format(Date(wakeUpTime))}")
                .setContentIntent(getPendingIntentWithStack(context, MainActivity::class.java))
                .setOngoing(true)
                .addAction(R.drawable.bottom_sheet_unclicked, "Stop", stopPendingIntent)


            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER, true)


            return nBuilder.build()

        }


        private fun getBasicNotificationBuilder(context: Context, channelId: String, playSound: Boolean)
                : NotificationCompat.Builder{
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.btn_google_signin_light_disabled)
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

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_MUTABLE)
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