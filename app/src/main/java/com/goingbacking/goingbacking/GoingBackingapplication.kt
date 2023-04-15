package com.goingbacking.goingbacking

import android.app.Application
import android.content.Context
import com.goingbacking.goingbacking.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GoingBackingapplication : BaseApplication() {

    init {
        instance = this
        setCrashHandler()
    }

    companion object {
        private var instance: GoingBackingapplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

}