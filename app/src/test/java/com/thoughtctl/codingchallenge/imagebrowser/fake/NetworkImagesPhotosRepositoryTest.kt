package com.thoughtctl.codingchallenge.imagebrowser.fake

import com.thoughtctl.codingchallenge.imagebrowser.data.NetworkImagerPhotosRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkImagesPhotosRepositoryTest {
    @Test
    fun networkImagerPhotosRepository_searchPhotosOfTheWeek_verifyPhotoList(){
        runTest {
            val repository = NetworkImagerPhotosRepository(imagerApiService = FakeImagerApiService())
            assertEquals(FakeDataSource.photosList.sortedByDescending { it.datetime }, repository.searchTopImagesOfTheWeek("", 0))
        }
    }

}