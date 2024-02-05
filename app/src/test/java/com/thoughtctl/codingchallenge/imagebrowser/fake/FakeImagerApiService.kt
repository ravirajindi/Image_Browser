package com.thoughtctl.codingchallenge.imagebrowser.fake

import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiResponse
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService

class FakeImagerApiService : ImagerApiService {
    override suspend fun searchTopImagesOfTheWeek(
        page: Int,
        searchQuery: String
    ): ImagerApiResponse {
        return FakeDataSource.successResponse
    }
}