package com.thoughtctl.codingchallenge.imagebrowser.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val AUTHORIZATION_HEADER = "Authorization"
// TODO: Store this sensitive information in secure way
private const val AUTHORIZATION_HEADER_VALUE = "Client-ID 2bc10ff2f7157d8"

interface ImagerApiService {
    @GET("gallery/search/top/week/")
    @Headers("$AUTHORIZATION_HEADER: $AUTHORIZATION_HEADER_VALUE")
    suspend fun searchTopImagesOfTheWeek(@Query("q") searchQuery : String) : ImagerApiResponse
}
