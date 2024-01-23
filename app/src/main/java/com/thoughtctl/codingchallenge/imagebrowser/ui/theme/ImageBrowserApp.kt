@file:OptIn(ExperimentalMaterial3Api::class)

package com.thoughtctl.codingchallenge.imagebrowser.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.ui.screens.DisplayMode
import com.thoughtctl.codingchallenge.imagebrowser.ui.screens.HomeScreen

/**
 * A container composable to hold the screen level composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageBrowserApp() {
    var checked by rememberSaveable { mutableStateOf(true) }
    val currentDisplayMode = if(checked) DisplayMode.GRID else DisplayMode.LIST
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ImageBrowserTopAppBar(
                checked,
                onDisplayModeChange = {
                    checked = it
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HomeScreen(currentDisplayMode)
        }
    }
}

/**
 * Composable to define app bar design and behavior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageBrowserTopAppBar(
    checked: Boolean,
    onDisplayModeChange : (Boolean) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            Text(text = stringResource(R.string.list))
            Switch(
                checked = checked,
                onCheckedChange = onDisplayModeChange,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(text = stringResource(R.string.grid), modifier = Modifier.padding(end = 8.dp))
        },
        modifier = modifier,
    )
}
