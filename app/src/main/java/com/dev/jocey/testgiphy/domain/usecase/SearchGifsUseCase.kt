package com.dev.jocey.testgiphy.domain.usecase

import androidx.paging.PagingData
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGifsUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    fun searchGifs(searchQuery: String): Flow<PagingData<GiphyEntity>> {
        return repository.searchGifs(searchQuery)
    }
}