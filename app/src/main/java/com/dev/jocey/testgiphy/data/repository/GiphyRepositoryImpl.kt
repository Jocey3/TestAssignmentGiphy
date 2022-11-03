package com.dev.jocey.testgiphy.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dev.jocey.testgiphy.data.local.db.GiphyDataBase
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.data.paging.DataRemoteMediator
import com.dev.jocey.testgiphy.data.remote.ApiGiphy
import com.dev.jocey.testgiphy.domain.repository.GiphyRepository
import com.dev.jocey.testgiphy.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GiphyRepositoryImpl @Inject constructor(
    private val db: GiphyDataBase,
    private val apiService: ApiGiphy
) : GiphyRepository {

    override fun searchGifs(searchQuery: String): Flow<PagingData<GiphyEntity>> {
        val pagingSourceFactory = { db.giphyDao().getAllNotBlockedGifs() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = DataRemoteMediator(
                db, apiService, searchQuery
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}