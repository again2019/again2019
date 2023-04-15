package com.goingbacking.goingbacking.exception

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.goingbacking.goingbacking.base.BaseActivityLifecycleCallbacks
import com.goingbacking.goingbacking.deprecatedview.ErrorActivity
import java.io.PrintWriter
import java.io.StringWriter
import android.os.Process
import kotlin.system.exitProcess

class CrashExceptionHandler(
    application: Application,
    private val crashlyticsExceptionHandler: Thread.UncaughtExceptionHandler,
) : Thread.UncaughtExceptionHandler {

    private var lastActivity: Activity? = null
    private var activityCount = 0

    init {
        application.registerActivityLifecycleCallbacks(
            object : BaseActivityLifecycleCallbacks() {

                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    if (isSkipActivity(activity)) return

                    lastActivity = activity
//                    super.onActivityCreated(activity, savedInstanceState)
                }

                override fun onActivityStarted(activity: Activity) {
                    if (isSkipActivity(activity)) return

                    activityCount ++
                    lastActivity = activity
//                    super.onActivityStarted(activity)
                }

                override fun onActivityStopped(activity: Activity) {

                    if (isSkipActivity(activity)) return

                    activityCount --
                    if (activityCount < 0) lastActivity = null
//                    super.onActivityStopped(activity)
                }
            }
        )

    }

    private fun isSkipActivity(activity: Activity) : Boolean {
        return activity is ErrorActivity // is: type이 맞는지 아닌지 check
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        lastActivity?.run {
            val stringWriter = StringWriter()
            throwable.printStackTrace(PrintWriter(stringWriter))

            /*
                예외 발생 당시의 호출 스택에 있던 method의 정보와 예외 결과 화면 출력
                printStackTrace()를 사용하는 경우 System.err로 쓰여져 제어하기 힘듬
                java reflection을 사용하기에 많은 overhead 발생

                e.getMessage(): 에러의 원인을 찾아내 간단히 출력
                e.toString(): 에러의 Exception 내용과 원인을 출력
                e.printStackTrace(): 발생의 근원지부터 차례대로 출력력
             */

            startErrorActivity(this, stringWriter.toString())
        }

        crashlyticsExceptionHandler.uncaughtException(thread, throwable)
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

    private fun startErrorActivity(activity: Activity, errorText: String) {
        activity.run {
            startActivity(
                ErrorActivity.getIntent(activity).apply {
                    putExtra(ErrorActivity.EXTRA_INTENT, intent)
                    putExtra(ErrorActivity.EXTRA_ERROR_TEXT, errorText)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            )
            finish()
        }
    }
}