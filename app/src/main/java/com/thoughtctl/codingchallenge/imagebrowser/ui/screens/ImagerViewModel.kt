package com.thoughtctl.codingchallenge.imagebrowser.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApi
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiResponse
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ImagerUiState {
    data class Success(val response : ImagerApiResponse) : ImagerUiState
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
        viewModelScope.launch {
            imagerUiState = try {
                val response = ImagerApi.retrofitService.searchTopImagesOfTheWeek(searchQuery)
                ImagerUiState.Success(response)
            } catch (e : IOException) {
                ImagerUiState.Error
            }
        }
    }
}