package com.dev.jocey.testgiphy.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {
     fun searchGifs(searchQuery: String): Flow<PagingData<GiphyEntity>>
     suspend fun blockGif(id: String)
}