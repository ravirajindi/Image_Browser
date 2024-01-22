package com.thoughtctl.codingchallenge.imagebrowser.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.thoughtctl.codingchallenge.imagebrowser.network.ImagerApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val imagerPhotosRepository : ImagerPhotosRepository
}

class DefaultAppContainer() : AppContainer {

    private val baseUrl =
        "https://api.imgur.com/3/"

    val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    val retrofitService : ImagerApiService by lazy {
        retrofit.create(ImagerApiService::class.java)
    }

    override val imagerPhotosRepository: ImagerPhotosRepository by lazy {
        NetworkImagerPhotosRepository(retrofitService)
    }
}