package com.thoughtctl.codingchallenge.imagebrowser.data

import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import java.io.IOException

/**
 * Repository to provide ImagerApiResponse
 */
interface ImagerPhotosRepository {
    suspend fun searchTopImagesOfTheWeek(searchQuery : String, page : Int) : List<Post>
}

class NetworkImagerPhotosRepository (private val imagerApiService: ImagerApiService): ImagerPhotosRepository {
    override suspend fun searchTopImagesOfTheWeek(searchQuery: String, page : Int): List<Post> {
        val response = imagerApiService.searchTopImagesOfTheWeek(page, searchQuery)
        if(response.success) {
            return response.data.sortedByDescending { it.datetime }
        } else {
            throw IOException("Error occurred while fetching data")
        }
    }
}
