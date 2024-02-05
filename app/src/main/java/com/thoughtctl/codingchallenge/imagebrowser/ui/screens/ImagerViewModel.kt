package com.thoughtctl.codingchallenge.imagebrowser.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thoughtctl.codingchallenge.imagebrowser.ImageBrowserApplication
import com.thoughtctl.codingchallenge.imagebrowser.data.ImagerPhotosPagingSource
import com.thoughtctl.codingchallenge.imagebrowser.data.ImagerPhotosRepository
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiResponse
import kotlinx.coroutines.flow.Flow

class ImagerViewModel (private val imagerPhotosRepository: ImagerPhotosRepository) : ViewModel() {

    private var currentQuery = ""

    private var imagerPhotosPagingSource : ImagerPhotosPagingSource? = null
        get() {
            if(field == null || field?.invalid == true) {
                field = ImagerPhotosPagingSource(searchQuery = currentQuery, repository = imagerPhotosRepository)
            }
            return field
        }

    val posts : Flow<PagingData<Post>> = Pager(config = PagingConfig(
        pageSize = 50,
        enablePlaceholders = false,
        )
    ) {
        imagerPhotosPagingSource ?: ImagerPhotosPagingSource(searchQuery = currentQuery, repository = imagerPhotosRepository)
    }.flow.cachedIn(viewModelScope)

    /**
     * Searches top images of the week from the Imager API Retrofit service and updates the
     * [ImagerApiResponse]
     */
    fun searchTopImagesOfTheWeek(searchQuery : String) {
        currentQuery = searchQuery
        imagerPhotosPagingSource?.invalidate()
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