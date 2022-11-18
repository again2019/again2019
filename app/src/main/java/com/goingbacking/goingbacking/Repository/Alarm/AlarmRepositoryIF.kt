package com.goingbacking.goingbacking.Repository.Alarm

import com.goingbacking.goingbacking.Model.*

interface AlarmRepositoryIF {

    // 맨 처음 로그인 시 month 초기화
    fun addFirstInitSaveTimeMonthInfo()
    // 맨 처음 로그인 시 year 초기화
    fun addFirstInitSaveTimeYearInfo()


    // day마다 초기화, 맨 처음 로그인 시 day 초기화
    fun addInitSaveTimeDayInfo()
    // month마다 초기화
    fun addInitSaveTimeMonthInfo()
    // year마다 초기화
    fun addInitSaveTimeYearInfo()

    // broadcast에서 매일 일정 가져오기
    fun getTodayInfo(result: (ArrayList<Event>) -> Unit)
    fun addTmpTimeInfo(currentTime: String, tmpTimeDTO: TmpTimeDTO)


    // month마다 whattodo 초기화
    fun addInitWhatToDoMonthInfo(whatToDOList : MutableSet<String>)
    // year마다 whattodo 초기화
    fun addInitWhatToDoYearInfo(whatToDOList : MutableSet<String>)

    // month마다 rank 초기화
    // year마다 rank 초기화
    fun addInitRankInfo()

}