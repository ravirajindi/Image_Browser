package com.thoughtctl.codingchallenge.imagebrowser.model

/**
 * GallerySearchResult from a search, which contains List<Post> holding query data,
 * and a String of network error state.
 */
sealed class GallerySearchResult {
    data class Success(val data: List<Post>) : GallerySearchResult()
    data class Error(val error: Exception) : GallerySearchResult()
}
