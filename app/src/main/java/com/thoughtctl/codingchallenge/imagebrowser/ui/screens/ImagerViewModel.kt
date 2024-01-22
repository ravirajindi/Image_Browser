package com.thoughtctl.codingchallenge.imagebrowser.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApi
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ImagerUiState {
    data class Success(val result : String) : ImagerUiState
    object Error : ImagerUiState
    object Loading : ImagerUiState
}

class ImagerViewModel : ViewModel() {

    /** The mutable State that stores the status of the most recent request */
    var imagerUiState : ImagerUiState by mutableStateOf(ImagerUiState.Loading)
        private set

    /**
     * Call searchTopImagesOfTheWeek() on init so we can display status immediately.
     */
    init {
        searchTopImagesOfTheWeek("cats")
    }

    /**
     * Searches top images of the week from the Imager API Retrofit service and updates the
     * [ImagerPhoto] [List] [MutableList].
     */
    fun searchTopImagesOfTheWeek(searchQuery : String) {
        try {
            viewModelScope.launch {
                val listResult = ImagerApi.retrofitService.searchTopImagesOfTheWeek(searchQuery)
                imagerUiState = ImagerUiState.Success(listResult)
            }
        } catch (e : IOException) {
            imagerUiState = ImagerUiState.Error
        }
    }
}