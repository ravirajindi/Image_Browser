package com.thoughtctl.codingchallenge.imagebrowser.data

import android.util.Log
import com.thoughtctl.codingchallenge.imagebrowser.network.Data
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import java.io.IOException

/**
 * Repository to provide ImagerApiResponse
 */
interface ImagerPhotosRepository {
    suspend fun searchTopImagesOfTheWeek(searchQuery : String) : List<Data>
}

class NetworkImagerPhotosRepository (private val imagerApiService: ImagerApiService): ImagerPhotosRepository {
    override suspend fun searchTopImagesOfTheWeek(searchQuery: String): List<Data> {
        Log.e("API Call", "searchTopImagesOfTheWeek")
        val response = imagerApiService.searchTopImagesOfTheWeek(searchQuery)
        if(response.success) {
            return response.data.sortedByDescending { it.datetime }
        } else {
            throw IOException("Error occurred while fetching data")
        }
    }
}