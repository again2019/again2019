package com.goingbacking.goingbacking.util

class Constants {
    companion object {
        // const val : 컴파일 시간에 결정되는 상수
        // 문자열이나 기본 타입으로 할당
        // 변수를 최상위 레벨로 선언하거나 object로 선언한 클래스에서만 사용 가능

        // splash
        const val DURATION : Long = 1000

        // 구글 로그인 관련 constant
        const val serverClientId = "1036649010261-p08hat5d9stl7qvdun1mg4fv94kj8nt6.apps.googleusercontent.com"
        // firebase 경로 관련 constant
        const val USERINFO = "UserInfo"
        const val CALENDARINFO = "CalendarInfo"
        const val DATE = "Date"
        const val DATE2 = "date"

        const val TMPTIMEINFO = "TmpTimeInfo"
        const val SAVETIMEINFO = "SaveTimeInfo"
        const val WHATTODOINFO = "WhatToDoInfo"
        const val RANKMONTHINFO = "RankMonthInfo"
        const val RANKYEARINFO = "RankYearInfo"

        const val DAY = "day"
        const val MONTH = "month"
        const val YEAR = "year"

        // firebase 결과 관련 constant
        const val SUCCESS = "success"
        const val FAIL = "fail"

        // second input 관련 constant
        const val USERTYPE = "userType"
        // third input 관련 constant
        const val WHATTODOLIST = "whatToDoList"

        // 알림 관련 constant
        const val ACTION_STOP = "stop"
        const val ACTION_START = "start"
        const val ACTION_READY = "ready"

        enum class TimerState {
            Stopped, Running
        }

        // forthMainFragment
        const val COUNT = "count"
        const val CHEERS = "cheers"
        const val LIKES = "likes"

        // 매일 울리는 알림 관련 constant
        // countReceiver 관련 constant
        const val ID = "id"
        const val TYPE = "type"
        const val CHANNEL = "channel"
        const val VALUE = 3000 // 매일 울리는 알림 channel value
        const val MOVETIME = "통근 시간"
        const val END_TIME = "end_time"

        // doingReceiver 관련 constant
        const val FIRST_START_FOREGROUND = "FIRST_START_FOREGROUND"
        const val START_FOREGROUND = "START_FOREGROUND"
        const val FINISH_FOREGROUND = "FINISH_FOREGROUND"
        const val WAKEUPTIME = "wakeUpTime"
        const val CURRENTTIME = "currentTime"
        const val DURATION2 = "duration"




    }

}