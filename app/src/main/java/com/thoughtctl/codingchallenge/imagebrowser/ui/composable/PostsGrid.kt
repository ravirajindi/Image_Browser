package com.thoughtctl.codingchallenge.imagebrowser.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.thoughtctl.codingchallenge.imagebrowser.model.Post

/**
 * Composable to show posts in a grid of items
 */
@Composable
fun PostsGrid(posts: LazyPagingItems<Post>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(posts.itemCount) {
                index ->
            if(posts[index] != null) {
                GridPostCard(
                    post = posts[index]!!,
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxHeight(),
                )
            }
        }
    }
}