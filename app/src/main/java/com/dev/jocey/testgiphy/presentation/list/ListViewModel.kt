package com.dev.jocey.testgiphy.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.domain.usecase.BlockGifUseCase
import com.dev.jocey.testgiphy.domain.usecase.SearchGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val newsUseCase: SearchGifsUseCase,
    private val blockGifUseCase: BlockGifUseCase
) : ViewModel() {
    private val _gifs: MutableStateFlow<PagingData<GiphyEntity>?> = MutableStateFlow(null)
    val gifs: Flow<PagingData<GiphyEntity>> = _gifs.filterNotNull()

    fun launchSearch(searchQuery: String) {
        newsUseCase.searchGifs(searchQuery).cachedIn(viewModelScope).onEach { results ->
            _gifs.emit(results)
        }.launchIn(viewModelScope)
    }

    fun blockGifById(id: String) =viewModelScope.launch {
        blockGifUseCase.blockGif(id)
    }
}
