package com.dev.jocey.testgiphy.data.paging

import androidx.paging.ExperimentalPagingApi
import com.dev.jocey.testgiphy.data.local.db.GiphyDataBase
import com.dev.jocey.testgiphy.data.remote.ApiGiphy
import javax.inject.Inject

@ExperimentalPagingApi
class DataRemoteMediator @Inject constructor(
    private val db: GiphyDataBase,
    private val apiService: ApiGiphy
) {
}