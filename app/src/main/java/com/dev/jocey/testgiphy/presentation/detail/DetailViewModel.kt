package com.dev.jocey.testgiphy.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.domain.usecase.SearchGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val newsUseCase: SearchGifsUseCase
) : ViewModel() {
    private val _gifs: MutableStateFlow<PagingData<GiphyEntity>?> = MutableStateFlow(null)
    val gifs: Flow<PagingData<GiphyEntity>> = _gifs.filterNotNull()

    private val _chan = MutableLiveData(false)
    val chan: LiveData<Boolean> = _chan

    fun launchSearch(searchQuery: String) {
        newsUseCase.searchGifs(searchQuery).cachedIn(viewModelScope).onEach { results ->
            _gifs.emit(results).also {
                delay(100)
                if (_chan.value == false) {
                    _chan.value = true
                }
            }
        }.launchIn(viewModelScope)
    }

}
