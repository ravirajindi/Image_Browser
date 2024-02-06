package com.thoughtctl.codingchallenge.imagebrowser.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thoughtctl.codingchallenge.imagebrowser.data.ImagerPhotosRepository.Companion.NETWORK_PAGE_SIZE
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import okio.IOException
import retrofit2.HttpException

class ImagerPhotosPagingSource(
    private val searchQuery : String,
    private val service: ImagerApiService
) : PagingSource<Int, Post>() {
    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val position = params.key ?: IMGUR_STARTING_PAGE_INDEX
        return try {
            val response = service.searchTopImagesOfTheWeek(position, searchQuery)
            val posts = response.data
            val nextKey = if (posts.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = posts,
                prevKey = if (position == IMGUR_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
