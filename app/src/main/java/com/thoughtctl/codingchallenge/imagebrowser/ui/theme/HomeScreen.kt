package com.thoughtctl.codingchallenge.imagebrowser.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

/**
 * Composable to define root of the home screen
 */
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
     /*LoadingScreen(
         modifier = modifier
     )*/

    ResultScreen(
        result = stringResource(R.string.placeholder_result),
        modifier = modifier,
    )

    /*ErrorScreen(
        modifier = modifier.fillMaxSize()
    )*/
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
fun ResultScreen(result : String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = result)
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
            result = stringResource(R.string.placeholder_result),
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