package com.goingbacking.goingbacking.utils

import android.app.Activity
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.example.presentation.ui.splash.SplashActivity
import java.lang.ref.WeakReference


val Activity.isActiveActivity : Boolean
    get() = (!this.isChangingConfigurations
            && !this.isFinishing
            && this.window.decorView.windowToken != null)

val Activity.isOwnedActivity : Boolean
    get() = (this is GoingBackingActivity)

internal object LastActivityUtils: BaseUtils() {
    private const val CONST_BACKGROUND_TIME = 3000L

    var _lastActivity: WeakReference<Activity>? = null

    val lastActivity: Activity?
        get() {
            val lastActivity = _lastActivity?.get() ?: return null
            if (!lastActivity.isActiveActivity) return null

            return lastActivity
        }

    var currentTime: Long? = null

    var wasInBackGround: Boolean = false

    private val activityLifecycleCallback = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        }

        override fun onActivityStarted(activity: Activity) {
//            if (!activity.isOwnedActivity) return
            _lastActivity = WeakReference(activity)

            if (currentTime != null && ((System.currentTimeMillis() - currentTime!!) > CONST_BACKGROUND_TIME)) {
                wasInBackGround = true
            }
            currentTime = null
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
            wasInBackGround = false
        }

        override fun onActivityStopped(activity: Activity) {
            if (_lastActivity?.get() === activity && _lastActivity?.get() !is SplashActivity) {
                currentTime = System.currentTimeMillis()
                _lastActivity = null
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

    }

    override fun initalize(context: Context) {
        super.initalize(context)
        val application = applicationContext as Application
        application.registerActivityLifecycleCallbacks(activityLifecycleCallback)

    }

    @Throws(IllegalStateException::class)
    fun requireLastActivity() : Activity {
        return lastActivity
            ?: throw java.lang.IllegalStateException("lastActivity can not be null")
    }
}