package com.goingbacking.goingbacking

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class TodoWorker(val context: Context, val params: WorkerParameters):Worker(context, params) {
    override fun doWork(): Result {

        NotificationHelper(context).createNotification()


        return Result.success()
    }


}