package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Repository.MainRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    val mainRepository: MainRepositoryIF
        ) : ViewModel() {

        private val _userInfoDTOs = MutableLiveData<UiState<UserInfoDTO>>()
            val userInfoDTO : LiveData<UiState<UserInfoDTO>>
                get() = _userInfoDTOs

    fun getFifthUserInfo()  {
            _userInfoDTOs.value = UiState.Loading
            mainRepository.getFifthUserInfo { _userInfoDTOs.value = it }
            }

}