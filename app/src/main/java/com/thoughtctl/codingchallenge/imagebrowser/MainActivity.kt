package com.thoughtctl.codingchallenge.imagebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.thoughtctl.codingchallenge.imagebrowser.ui.theme.ImageBrowserApp
import com.thoughtctl.codingchallenge.imagebrowser.ui.theme.ImageBrowserTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * launcher activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageBrowserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageBrowserApp()
                }
            }
        }
    }
}
