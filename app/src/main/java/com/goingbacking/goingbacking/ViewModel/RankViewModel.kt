package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Repository.RankRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RankViewModel @Inject constructor(
    val rankRepository :RankRepositoryIF
):ViewModel() {


}