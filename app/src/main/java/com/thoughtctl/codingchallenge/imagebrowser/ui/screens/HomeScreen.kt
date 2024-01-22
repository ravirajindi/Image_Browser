package com.thoughtctl.codingchallenge.imagebrowser.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.network.Data
import com.thoughtctl.codingchallenge.imagebrowser.ui.theme.ImageBrowserTheme

/**
 * Composable to define root of the home screen
 */
@Composable
fun HomeScreen(
    imagerUiState: ImagerUiState,
    modifier: Modifier = Modifier
) {
    when (imagerUiState) {
        is ImagerUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ImagerUiState.Success -> ResultScreen(
            imagerUiState.posts, modifier = modifier.fillMaxWidth()
        )
        is ImagerUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}

/**
 * A placeholder composable to show the loading UI while loading data is in progress
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = Modifier.size(200.dp)
    )
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
        ErrorScreen(modifier = Modifier)
    }
}
