package com.example.domain.repository

import com.example.domain.model.TmpTimeModel

interface FirstRepository {
    suspend fun getTmpTime() : ArrayList<TmpTimeModel>

}