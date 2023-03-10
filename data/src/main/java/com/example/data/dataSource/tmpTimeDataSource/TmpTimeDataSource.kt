package com.example.data.dataSource.tmpTimeDataSource

import com.example.data.entity.TmpTimeEntity

interface TmpTimeDataSource {

     suspend fun getTmpTimDTO() : ArrayList<TmpTimeEntity>
}