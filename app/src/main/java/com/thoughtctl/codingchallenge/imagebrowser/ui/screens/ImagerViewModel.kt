package com.thoughtctl.codingchallenge.imagebrowser.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.thoughtctl.codingchallenge.imagebrowser.ImageBrowserApplication
import com.thoughtctl.codingchallenge.imagebrowser.data.ImagerPhotosRepository
import com.thoughtctl.codingchallenge.imagebrowser.network.Data
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiResponse
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ImagerUiState {
    data class Success(val posts : List<Data>) : ImagerUiState
    object Error : ImagerUiState
    object Loading : ImagerUiState
    object Idle : ImagerUiState
}

class ImagerViewModel (private val imagerPhotosRepository: ImagerPhotosRepository) : ViewModel() {

    /** The mutable State that stores the status of the most recent request */
    var imagerUiState : ImagerUiState by mutableStateOf(ImagerUiState.Idle)
        private set

    /**
     * Searches top images of the week from the Imager API Retrofit service and updates the
     * [ImagerApiResponse]
     */
    fun searchTopImagesOfTheWeek(searchQuery : String) {
        imagerUiState = ImagerUiState.Loading
        viewModelScope.launch {
            imagerUiState = try {
                val posts = imagerPhotosRepository.searchTopImagesOfTheWeek(searchQuery)
                ImagerUiState.Success(posts)
            } catch (e : IOException) {
                ImagerUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ImageBrowserApplication)
                val imagerPhotosRepository = application.container.imagerPhotosRepository
                ImagerViewModel(imagerPhotosRepository = imagerPhotosRepository)
            }
        }
    }
}