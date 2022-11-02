package com.dev.jocey.testgiphy.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.jocey.testgiphy.data.remote.ApiGiphy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor() : ViewModel() {
    fun getSearch() {
        viewModelScope.launch {

        }
    }
}