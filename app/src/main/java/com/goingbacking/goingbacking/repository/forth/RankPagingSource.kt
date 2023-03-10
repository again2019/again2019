package com.goingbacking.goingbacking.repository.forth

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.goingbacking.goingbacking.model.NewSaveTimeYearDTO
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException

class RankPagingSource(
    private val queryRankingInfo : Query
) : PagingSource<QuerySnapshot, NewSaveTimeYearDTO>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, NewSaveTimeYearDTO>): QuerySnapshot? {

        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, NewSaveTimeYearDTO> {
        return try {
            val currentPage = params.key ?: queryRankingInfo.get().await()
            val lastVisibleInfo = currentPage.documents[currentPage.size() - 1]
            val nextPage = queryRankingInfo.startAfter(lastVisibleInfo).get().await()

            LoadResult.Page (
                data = currentPage.toObjects(NewSaveTimeYearDTO :: class.java),
                prevKey = null,
                nextKey = nextPage
            )

        } catch(e : IOException) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }

    }
}