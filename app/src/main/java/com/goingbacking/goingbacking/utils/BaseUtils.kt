package com.goingbacking.goingbacking.utils

import android.content.Context
import com.goingbacking.goingbacking.base.BaseApplication

internal abstract class BaseUtils {
    protected lateinit var applicationContext: Context

    internal open fun initalize(context: Context) {
        applicationContext = context.applicationContext
    }
}