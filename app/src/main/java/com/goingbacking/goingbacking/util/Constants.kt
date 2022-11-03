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
        const val TMPTIMEINFO = "TmpTimeInfo"
        const val SAVETIMEINFO = "SaveTimeInfo"
        const val WHATTODOINFO = "WhatToDoInfo"
        const val RANKMONTHINFO = "RankMonthInfo"
        const val RANKYEARINFO = "RankYearInfo"

        const val DAY = "day"
        const val MONTH = "month"
        const val YEAR = "Year"

        // firebase 결과 관련 constant
        const val SUCCESS = "success"
        const val FAIL = "fail"

        // second input 관련 constant
        const val USERTYPE = "userType"
        const val WHATTODOLIST = "whatToDoList"
        const val YYYYMM = "yyyy-MM"


        // 알림 관련 constant
        const val ACTION_STOP = "stop"
        const val ACTION_START = "start"
        const val ACTION_READY = "ready"

        enum class TimerState {
            Stopped, Running
        }



    }

}