@file:OptIn(ExperimentalComposeUiApi::class)

package com.thoughtctl.codingchallenge.imagebrowser.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.ui.composable.ErrorComposable
import com.thoughtctl.codingchallenge.imagebrowser.ui.composable.PostsComposable
import com.thoughtctl.codingchallenge.imagebrowser.ui.composable.SearchTextField

/**
 * enum class to hold possible values of display modes.
 * These modes determine whether the data will be shown in the grid or list format
 */
enum class DisplayMode {
    LIST,
    GRID
}

/**
 * Composable to define root of the home screen
 */
@Composable
fun HomeScreen(
    displayMode: DisplayMode = DisplayMode.LIST,
    modifier: Modifier = Modifier
) {
    val imagerViewModel : ImagerViewModel = viewModel()

    val posts = imagerViewModel.posts.collectAsLazyPagingItems()

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // search text field
        SearchTextField(
            label = R.string.search_hint,
            KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            query = searchQuery,
            onQueryChange =  {
                searchQuery = it
                if(searchQuery.isNotBlank()) {
                    imagerViewModel.searchTopImagesOfTheWeek(searchQuery)
                } },
            onTextClear = {
                searchQuery = ""
            },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        when (val state = posts.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                LoadingIndicator()
            }
            is LoadState.Error -> {
                ErrorComposable(imagerViewModel, searchQuery)
            }
        }
        when (val state = posts.loadState.refresh) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                LoadingIndicator()
            }
            is LoadState.Error -> {
                ErrorComposable(imagerViewModel, searchQuery)
            }
        }
        PostsComposable(posts = posts, displayMode = displayMode)

        when (val state = posts.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                LoadingIndicator()
            }
            is LoadState.Error -> {
                ErrorComposable(imagerViewModel, searchQuery)
            }
        }
    }
}

