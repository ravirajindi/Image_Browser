package com.thoughtctl.codingchallenge.imagebrowser.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.thoughtctl.codingchallenge.imagebrowser.model.Post

/**
 * Composable to show posts in a list of items
 */
@Composable
fun PostsList(posts: LazyPagingItems<Post>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(posts.itemCount) {
                index ->
            if(posts[index] != null) {
                ListPostCard(
                    post = posts[index]!!,
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxHeight(),
                )
            }
        }
    }
}