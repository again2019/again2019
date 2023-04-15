package com.goingbacking.goingbacking.base

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

abstract class ActivityLauncher<T: Activity> {
    protected abstract val activityClass: KClass<T>

    open fun getIntent(context: Context) : Intent {
        return Intent(context, activityClass.java)
    }

    open fun getPendingIntent(
        context: Context,
        requestCode : Int = 0,
    ) : PendingIntent {
        return PendingIntent.getActivity(
            context,
            requestCode,
            Intent(context, activityClass.java),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    open fun startActivity(context: Context) {
        context.startActivity(getIntent(context))
    }

    fun startActivityWithClear(context: Context) {
        context.startActivity(getIntent(context).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    fun startActivityWithClearTask(context: Context) {
        context.startActivity(getIntent(context).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        })
    }

    fun startActivityWithNewTask(context: Context) {
        context.startActivity(getIntent(context).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        })
    }
}