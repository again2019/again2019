package com.goingbacking.goingbacking

import android.content.Context
import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit

class Worker(appContext: Context, parameters: WorkerParameters) : CoroutineWorker(appContext, parameters) {

    companion object{
        const val WORK_NAME = "WorkApplication"
    }

    override suspend fun doWork(): Result {

        try {
            Log.e(WORK_NAME, "DoWork")

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<Worker>().
            setInitialDelay(getOneMinIntervalTime(), TimeUnit.MILLISECONDS).
            setConstraints(constraints).build()

            WorkManager.getInstance(applicationContext).enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)

        }catch (e: Exception){
            Result.retry()
        }

        return Result.success()
    }

}