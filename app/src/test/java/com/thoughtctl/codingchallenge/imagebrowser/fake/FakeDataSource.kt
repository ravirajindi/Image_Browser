package com.thoughtctl.codingchallenge.imagebrowser.fake

import com.thoughtctl.codingchallenge.imagebrowser.network.Data
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiResponse

object FakeDataSource {
    val photosList = listOf(
        Data(
            id = "1",
            title = "post 1",
            datetime = 100,
            isAlbum = false,
            link = "image_url_1"
        ),
        Data(
            id = "2",
            title = "post 2",
            datetime = 200,
            isAlbum = true,
            cover = "cover_image_url",
            imagesCount = 3,
            link = "album_1_url"
        )
    )
    val successResponse = ImagerApiResponse(
        data = photosList,
        status = 200,
        success = true
    )
}