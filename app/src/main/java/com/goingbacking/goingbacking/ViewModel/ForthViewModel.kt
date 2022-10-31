package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Repository.ForthRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ForthViewModel @Inject constructor(
    val forthRepository: ForthRepositoryIF
) : ViewModel() {



}