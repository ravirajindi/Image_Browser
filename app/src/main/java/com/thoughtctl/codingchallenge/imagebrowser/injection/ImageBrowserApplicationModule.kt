package com.thoughtctl.codingchallenge.imagebrowser.injection

import android.content.Context
import com.example.android.codelabs.paging.db.ImageBrowserDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class ImageBrowserApplicationModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object ImagerPhotosRepositoryModule {

        private val IMAGER_BASE_URL =
            "https://api.imgur.com/3/"

        val json = Json {
            ignoreUnknownKeys = true
        }

        @Provides
        fun providesImagerApiService(): ImagerApiService {
            return Retrofit.Builder()
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .baseUrl(IMAGER_BASE_URL)
                .build()
                .create(ImagerApiService::class.java)
        }

        @Provides
        fun providesImageBrowserDatabase(@ApplicationContext context: Context) : ImageBrowserDatabase {
            return ImageBrowserDatabase.getInstance(context)
        }
    }
}
