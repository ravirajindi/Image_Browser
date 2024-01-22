package com.thoughtctl.codingchallenge.imagebrowser.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.imgur.com/3/"

private const val AUTHORIZATION_HEADER = "Authorization"
private const val CLIENT_ID = "Client-ID 2bc10ff2f7157d8"

val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ImagerApiService {
    @GET("gallery/search/top/week/")
    @Headers("$AUTHORIZATION_HEADER: $CLIENT_ID")
    suspend fun searchTopImagesOfTheWeek(@Query("q") searchQuery : String) : ImagerApiResponse
}

object ImagerApi {
    val retrofitService : ImagerApiService by lazy {
        retrofit.create(ImagerApiService::class.java)
    }
}