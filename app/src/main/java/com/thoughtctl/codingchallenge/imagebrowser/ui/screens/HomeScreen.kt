@file:OptIn(ExperimentalComposeUiApi::class)

package com.thoughtctl.codingchallenge.imagebrowser.ui.screens

import android.icu.text.SimpleDateFormat
import android.text.format.DateUtils
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.network.Data
import com.thoughtctl.codingchallenge.imagebrowser.ui.theme.ImageBrowserTheme
import java.util.Date
import java.util.Locale

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
    val imagerViewModel : ImagerViewModel = viewModel(factory = ImagerViewModel.Factory)
    val imagerUiState = imagerViewModel.imagerUiState

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

        when (imagerUiState) {
            is ImagerUiState.Idle -> {
                // do nothing
            }
            is ImagerUiState.Loading -> LoadingScreen(
                modifier = modifier.fillMaxSize()
            )

            is ImagerUiState.Success -> PostsScreen(
                posts = imagerUiState.posts,
                displayMode = displayMode,
                modifier = modifier
            )

            is ImagerUiState.Error -> ErrorScreen(
                imagerViewModel,
                searchQuery,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

/**
 * A placeholder composable to show the loading UI while loading data is in progress
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Composable to show search bar in the top of the screen.
 * TODO: Implement debouncing to handle search query in efficient manner
 */
@Composable
fun SearchTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    query: String,
    onQueryChange: (String) -> Unit,
    onTextClear:() -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        trailingIcon = {
            if(query.isNotBlank()) {
                IconButton(onClick = onTextClear) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = stringResource(R.string.clear_icon)
                    )
                }
            }
        },
        onValueChange = onQueryChange,
        placeholder = { Text(text = stringResource(label)) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus(true)
            }
        ),
        modifier = modifier
    )
}

/**
 * Composable to show posts either in a grid of items or list of items
 * depending on the current display mode selected by used
 */
@Composable
fun PostsScreen(
    modifier: Modifier = Modifier,
    posts : List<Data>,
    displayMode: DisplayMode = DisplayMode.LIST,
) {
    if(posts.isEmpty()) {
        Text(text = stringResource(R.string.no_results_found))
    }
    else {
        when (displayMode) {
            DisplayMode.LIST -> PostsListScreen(posts, modifier)
            DisplayMode.GRID -> PostsGridScreen(posts, modifier)
        }
    }
}

/**
 * Composable to show posts in a list of items
 */
@Composable
fun PostsListScreen(posts: List<Data>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(posts) {
            post -> ListPostCard(
            post = post,
            modifier = modifier
                .padding(4.dp)
                .fillMaxHeight(),
            )
        }
    }
}

/**
 * Composable to show posts in a grid of items
 */
@Composable
fun PostsGridScreen(posts: List<Data>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = posts, key = { post -> post.id }) {
            post -> GridPostCard(
            post = post,
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
            )
        }
    }
}

/**
 * Composable to show post as a list item.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListPostCard(
    post : Data, modifier: Modifier = Modifier
) {
    val imageUrl = getImageUrlForPost(post)
    Card(
        modifier = modifier.fillMaxHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row (
            modifier = modifier
                .fillMaxHeight()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = post.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Column (
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                val imageCount = post.imagesCount ?: 0
                Text(
                    text = pluralStringResource(R.plurals.image_count, imageCount, imageCount),
                )

                val formatter = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
                val postDate = Date(post.datetime * DateUtils.SECOND_IN_MILLIS)
                Text(
                    text = "Posted on : ${formatter.format(postDate)}",
                )
            }
        }
    }
}

/**
 * Composable to show post as a grid item.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridPostCard(
    post : Data, modifier: Modifier = Modifier
) {
    val imageUrl = getImageUrlForPost(post)
    Card(
        modifier = Modifier.fillMaxHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                var isLoading by rememberSaveable {
                    mutableStateOf(true)
                }

                if(isLoading) {
                    CircularProgressIndicator()
                }
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.ic_broken_image),
                    contentDescription = post.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(150.dp),
                    onLoading = {
                        isLoading = true
                    },
                    onSuccess = {
                        isLoading = false
                    },
                    onError = {
                        isLoading = false
                    }
                )
            }
            Column (
                modifier = Modifier.height(150.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )

                val imageCount = post.imagesCount ?: 0
                Text(
                    text = pluralStringResource(R.plurals.image_count, imageCount, imageCount),
                    modifier = Modifier.padding(4.dp)
                )

                val formatter = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
                val postDate = Date(post.datetime * DateUtils.SECOND_IN_MILLIS)
                Text(
                    text = "Posted on : ${formatter.format(postDate)}",
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

/**
 * Retrieve image url for given post.
 * Some posts contain multiple images and they fall under album category of post.
 * In suc cases, url for the first image should be returned.
 * While others have single image and therefore,
 * url for the image is directly embedded in the outer Data class.
 *
 * TODO: Handle mp4/gif urls more appropriately and get rid of the workaround
 */
fun getImageUrlForPost(post: Data) : String {
    val imageUrl = if(post.isAlbum && !post.images.isNullOrEmpty()) {
        post.images[0].link
    }
    else {
        post.link
    }
    return imageUrl.replace("mp4", "jpg")
}

/**
 * A placeholder composable to show the result after successful loading of data
 */
@Composable
fun ResultScreen(posts : List<Data>, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = "${posts.size} post found")
    }
}

/**
 * Composable to show the error UI in case of failure to load the data
 */
@Composable
fun ErrorScreen(
    imagerViewModel: ImagerViewModel,
    searchQuery : String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier =Modifier.padding(16.dp),
        )
        Button(
            onClick = {
                imagerViewModel.searchTopImagesOfTheWeek(searchQuery)
            }
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    ImageBrowserTheme {
        LoadingScreen(modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    ImageBrowserTheme {
        val mockData = List(10) { Data("$it", title = "", isAlbum = false, datetime = 100, link = "") }
        PostsGridScreen(mockData)
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ImageBrowserTheme {
        ResultScreen(
            posts = listOf(
                Data(
                    id = "1",
                    title = "post 1",
                    datetime = 100,
                    isAlbum = false,
                    link = "image_url_1"
                ),
                Data(
                    id = "2",
                    title = "post 2",
                    datetime = 200,
                    isAlbum = true,
                    cover = "cover_image_url",
                    imagesCount = 3,
                    link = "album_1_url"
                )
            ),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ImageBrowserTheme {
        val imagerViewModel : ImagerViewModel = viewModel(factory = ImagerViewModel.Factory)
        ErrorScreen(
            imagerViewModel = imagerViewModel,
            searchQuery = ""
        )
    }
}
