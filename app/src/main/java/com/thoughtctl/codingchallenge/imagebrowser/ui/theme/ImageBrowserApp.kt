@file:OptIn(ExperimentalMaterial3Api::class)

package com.thoughtctl.codingchallenge.imagebrowser.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.ui.screens.HomeScreen
import com.thoughtctl.codingchallenge.imagebrowser.ui.screens.ImagerViewModel

/**
 * A container composable to hold the screen level composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageBrowserApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ImageBrowserTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val imagerViewModel : ImagerViewModel = viewModel(factory = ImagerViewModel.Factory)
            HomeScreen(imagerUiState = imagerViewModel.imagerUiState)
        }
    }
}

/**
 * Composable to define app bar design and behavior
 */
@Composable
fun ImageBrowserTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        modifier = modifier,
    )
}
