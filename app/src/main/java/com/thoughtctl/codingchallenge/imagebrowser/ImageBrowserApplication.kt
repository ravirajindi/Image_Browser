package com.thoughtctl.codingchallenge.imagebrowser

import android.app.Application
import com.thoughtctl.codingchallenge.imagebrowser.data.AppContainer
import com.thoughtctl.codingchallenge.imagebrowser.data.DefaultAppContainer

class ImageBrowserApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}