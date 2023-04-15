package com.goingbacking.goingbacking.base

import android.app.Application
import com.goingbacking.goingbacking.exception.CrashExceptionHandler
import com.goingbacking.goingbacking.utils.LastActivityUtils

abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LastActivityUtils.initalize(this)

    }

    open fun setCrashHandler() {
        val crashlyticsExceptionHandler = Thread.getDefaultUncaughtExceptionHandler() ?: return
        Thread.setDefaultUncaughtExceptionHandler(
            CrashExceptionHandler(
                this,
                crashlyticsExceptionHandler
            )
        )
    }

}