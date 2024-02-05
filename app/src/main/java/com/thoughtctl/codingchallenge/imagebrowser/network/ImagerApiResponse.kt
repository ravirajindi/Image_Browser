package com.thoughtctl.codingchallenge.imagebrowser.network
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagerApiResponse(
    @SerialName("data")
    val data: List<Post>,
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean
)
