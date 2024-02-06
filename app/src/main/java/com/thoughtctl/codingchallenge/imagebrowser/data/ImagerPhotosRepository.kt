package com.thoughtctl.codingchallenge.imagebrowser.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.codelabs.paging.db.ImageBrowserDatabase
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Imgur page API is 0 based: https://api.imgur.com/endpoints/gallery#gallery-search
internal const val IMGUR_STARTING_PAGE_INDEX = 0

/**
 * Repository to provide ImagerApiResponse
 */
class ImagerPhotosRepository @Inject constructor(
    private val imagerApiService: ImagerApiService,
    private val database: ImageBrowserDatabase
) {
    /**
     * Search posts whose title matches the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    @OptIn(ExperimentalPagingApi::class)
    fun searchTopImagesOfTheWeek(searchQuery: String): Flow<PagingData<Post>> {
        val dbQuery = "%${searchQuery.replace(' ', '%')}%"
        val pagingSourceFactory =  { database.postsDao().postsByName(dbQuery)}
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = ImgurPhotosRemoteMediator(searchQuery, imagerApiService, database),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 60
    }
}
