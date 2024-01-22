package com.thoughtctl.codingchallenge.imagebrowser.fake

import com.thoughtctl.codingchallenge.imagebrowser.data.ImagerPhotosRepository
import com.thoughtctl.codingchallenge.imagebrowser.network.Data

class FakeNetworkImagerPhotosRepository : ImagerPhotosRepository {
    override suspend fun searchTopImagesOfTheWeek(searchQuery: String): List<Data> {
        return FakeDataSource.photosList
    }
}