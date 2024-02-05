package com.thoughtctl.codingchallenge.imagebrowser.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("id")
    val id: String,
    @SerialName("type")
    val type: String,
    @SerialName("link")
    val link: String,
    @SerialName("gifv")
    val gifLink: String? = null,
)