package com.thoughtctl.codingchallenge.imagebrowser.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android.codelabs.paging.db.ImageBrowserDatabase
import com.example.android.codelabs.paging.db.RemoteKeys
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class ImgurPhotosRemoteMediator(
    private val query: String,
    private val service: ImagerApiService,
    private val postDatabase: ImageBrowserDatabase
) : RemoteMediator<Int, Post>(){
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeysForClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: IMGUR_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = service.searchTopImagesOfTheWeek(page, query)

            val posts = apiResponse.data
            val endOfPaginationReached = posts.isEmpty()
            postDatabase.withTransaction {
                // clear all tables in the database
                if(loadType == LoadType.REFRESH) {
                    postDatabase.remoteKeysDao().clearRemoteKeys()
                    postDatabase.postsDao().clearPosts()
                }

                val prevKey = if(page == IMGUR_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1
                val keys = posts.map {
                    RemoteKeys(postId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                postDatabase.remoteKeysDao().insertAll(keys)
                postDatabase.postsDao().insertAll(posts)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception : IOException) {
            return MediatorResult.Error(exception)
        } catch (exception : HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, Post>) : RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
                post ->
            // Get the remote keys of the first items retrieved
            postDatabase.remoteKeysDao().remoteKeysForPostId(post.id)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Post>) : RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
                post ->
            // Get the remote keys of the last items retrieved
            postDatabase.remoteKeysDao().remoteKeysForPostId(post.id)
        }
    }

    private suspend fun getRemoteKeysForClosestToCurrentPosition(state: PagingState<Int, Post>) : RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let {
                position ->
            state.closestItemToPosition(position)?.id?.let {
                    postId ->
                postDatabase.remoteKeysDao().remoteKeysForPostId(postId)
            }
        }
    }
}