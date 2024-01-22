package com.thoughtctl.codingchallenge.imagebrowser.fake

import com.thoughtctl.codingchallenge.imagebrowser.network.Data
import com.thoughtctl.codingchallenge.imagebrowser.rules.TestDispatcherRule
import com.thoughtctl.codingchallenge.imagebrowser.ui.screens.ImagerUiState
import com.thoughtctl.codingchallenge.imagebrowser.ui.screens.ImagerViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ImagerViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun imagerViewModel_getImagerPhotos_verifyImagerUiStateSuccess() =
        runTest {
            val imagerViewModel = ImagerViewModel(imagerPhotosRepository = FakeNetworkImagerPhotosRepository())
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
            assertEquals(
                ImagerUiState.Success(photosList),
                imagerViewModel.imagerUiState
            )
        }
}