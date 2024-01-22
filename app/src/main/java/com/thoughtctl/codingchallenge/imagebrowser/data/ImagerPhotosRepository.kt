package com.thoughtctl.codingchallenge.imagebrowser.data

import com.thoughtctl.codingchallenge.imagebrowser.network.Data
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService

/**
 * Repository to provide ImagerApiResponse
 */
interface ImagerPhotosRepository {
    suspend fun searchTopImagesOfTheWeek(searchQuery : String) : List<Data>
}

class NetworkImagerPhotosRepository (private val imagerApiService: ImagerApiService): ImagerPhotosRepository {
    override suspend fun searchTopImagesOfTheWeek(searchQuery: String): List<Data> {
        return imagerApiService.searchTopImagesOfTheWeek(searchQuery).data
    }
}