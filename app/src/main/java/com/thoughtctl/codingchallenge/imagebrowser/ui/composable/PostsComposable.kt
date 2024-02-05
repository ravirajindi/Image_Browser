package com.thoughtctl.codingchallenge.imagebrowser.ui.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.ui.screens.DisplayMode

/**
 * Composable to show posts either in a grid of items or list of items
 * depending on the current display mode selected by used
 */
@Composable
fun PostsComposable(
    modifier: Modifier = Modifier,
    posts : LazyPagingItems<Post>,
    displayMode: DisplayMode = DisplayMode.LIST,
) {
    if((posts.loadState.refresh is LoadState.NotLoading
                && posts.loadState.append is LoadState.NotLoading
                && posts.loadState.prepend is LoadState.NotLoading) && posts.itemCount == 0) {
        Text(text = stringResource(R.string.no_results_found))
    }
    else {
        when (displayMode) {
            DisplayMode.LIST -> PostsList(posts, modifier)
            DisplayMode.GRID -> PostsGrid(posts, modifier)
        }
    }
}