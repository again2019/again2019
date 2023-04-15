package com.goingbacking.goingbacking.base

import android.app.Application
import com.goingbacking.goingbacking.exception.CrashExceptionHandler

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun setCrashHandler() {
        val crashlyticsExceptionHandler = Thread.getDefaultUncaughtExceptionHandler() ?: return
        Thread.setDefaultUncaughtExceptionHandler(
            CrashExceptionHandler(
                this,
                crashlyticsExceptionHandler
            )
        )
    }

}