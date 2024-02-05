package com.thoughtctl.codingchallenge.imagebrowser.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thoughtctl.codingchallenge.imagebrowser.data.ImagerPhotosPagingSource
import com.thoughtctl.codingchallenge.imagebrowser.data.ImagerPhotosRepository
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ImagerViewModel @Inject constructor(private val imagerPhotosRepository: ImagerPhotosRepository) : ViewModel() {

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
}