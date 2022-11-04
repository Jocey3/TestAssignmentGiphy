package com.dev.jocey.testgiphy.domain.usecase

import com.dev.jocey.testgiphy.domain.repository.GiphyRepository
import javax.inject.Inject

class BlockGifUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend fun blockGif(id: String) {
        return repository.blockGif(id)
    }
}