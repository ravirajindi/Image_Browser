package com.thoughtctl.codingchallenge.imagebrowser.fake

import com.thoughtctl.codingchallenge.imagebrowser.network.Data
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiResponse
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService

class FakeImagerApiService : ImagerApiService {
    override suspend fun searchTopImagesOfTheWeek(searchQuery: String): ImagerApiResponse {
        return FakeDataSource.successResponse
    }
}