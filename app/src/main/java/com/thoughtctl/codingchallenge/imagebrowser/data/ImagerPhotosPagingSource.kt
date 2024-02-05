package com.thoughtctl.codingchallenge.imagebrowser.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import okio.IOException
import retrofit2.HttpException

class ImagerPhotosPagingSource(
    private val searchQuery : String,
    private val repository: ImagerPhotosRepository
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val currentPage = params.key ?: 0
            val result = repository.searchTopImagesOfTheWeek(searchQuery, currentPage)
            LoadResult.Page(
                data = result,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (result.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
