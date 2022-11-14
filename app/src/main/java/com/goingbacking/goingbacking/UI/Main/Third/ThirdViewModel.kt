package com.goingbacking.goingbacking.UI.Main.Third

import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepositoryIF
import com.goingbacking.goingbacking.Repository.Third.ThirdRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ThirdViewModel @Inject constructor(
    val thirdRepository: ThirdRepositoryIF
) : ViewModel() {



    
}