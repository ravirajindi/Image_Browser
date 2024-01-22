package com.thoughtctl.codingchallenge.imagebrowser.data

import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiResponse
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService

/**
 * Repository to provide ImagerApiResponse
 */
interface ImagerPhotosRepository {
    suspend fun searchTopImagesOfTheWeek(searchQuery : String) : ImagerApiResponse
}

class NetworkImagerPhotosRepository (private val imagerApiService: ImagerApiService): ImagerPhotosRepository {
    override suspend fun searchTopImagesOfTheWeek(searchQuery: String): ImagerApiResponse {
        return imagerApiService.searchTopImagesOfTheWeek(searchQuery)
    }
}