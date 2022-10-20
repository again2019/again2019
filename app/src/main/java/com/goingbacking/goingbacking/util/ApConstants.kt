package com.goingbacking.goingbacking

class AppConstants {
    companion object {
        const val ACTION_STOP = "stop"
//        const val ACTION_PAUSE = "pause"
//        const val ACTION_RESUME = "resume"
        const val ACTION_START = "start"
        const val ACTION_READY = "ready"

        enum class TimerState {
            Stopped, Paused, Running
        }
    }
}