package com.thoughtctl.codingchallenge.imagebrowser.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.imgur.com/3/"

private const val AUTHORIZATION_HEADER = "Authorization"
private const val CLIENT_ID = "Client-ID 2bc10ff2f7157d8"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ImagerApiService {
    @GET("gallery/search/top/week/")
    @Headers("$AUTHORIZATION_HEADER: $CLIENT_ID")
    suspend fun searchTopImagesOfTheWeek(@Query("q") searchQuery : String) : String
}

object ImagerApi {
    val retrofitService : ImagerApiService by lazy {
        retrofit.create(ImagerApiService::class.java)
    }
}