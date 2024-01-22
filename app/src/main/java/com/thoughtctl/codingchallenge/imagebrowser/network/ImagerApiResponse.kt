package com.thoughtctl.codingchallenge.imagebrowser.network
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagerApiResponse(
    @SerialName("data")
    val data: List<Data>,
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean
)

@Serializable
data class Data(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("datetime")
    val datetime: Int,
    @SerialName("is_album")
    val isAlbum: Boolean,
    @SerialName("cover")
    val cover: String? = null,
    @SerialName("images_count")
    val imagesCount: Int? = null,
    @SerialName("link")
    val link: String,
)