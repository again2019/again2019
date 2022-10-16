package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Repository.AlarmRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    val alarmRepository : AlarmRepositoryIF
) : ViewModel() {
}