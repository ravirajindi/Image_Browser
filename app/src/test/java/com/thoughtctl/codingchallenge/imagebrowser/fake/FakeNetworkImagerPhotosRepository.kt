package com.thoughtctl.codingchallenge.imagebrowser.fake

import com.thoughtctl.codingchallenge.imagebrowser.data.ImagerPhotosRepository
import com.thoughtctl.codingchallenge.imagebrowser.model.Post

class FakeNetworkImagerPhotosRepository : ImagerPhotosRepository {
    override suspend fun searchTopImagesOfTheWeek(searchQuery: String, page: Int): List<Post> {
        return FakeDataSource.photosList
    }
}