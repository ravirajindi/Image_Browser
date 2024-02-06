package com.thoughtctl.codingchallenge.imagebrowser.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.android.codelabs.paging.db.TypeConvertor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Entity(tableName = "posts")
@TypeConverters(TypeConvertor::class)
@Serializable
data class Post(
    @PrimaryKey(autoGenerate = false) @SerialName("id")
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
    @SerialName("gifv")
    val gifLink: String? = null,
    @SerialName("images")
    val images : List<Image>? = null
)

/**
 * Retrieve image url for given post.
 * Some posts contain multiple images and they fall under album category of post.
 * In suc cases, url for the first image should be returned.
 * While others have single image and therefore,
 * url for the image is directly embedded in the outer Data class.
 *
 * TODO: Handle mp4/gif urls more appropriately and get rid of the workaround
 */
fun Post.getImageUrlForPost() : String {
    val imageUrl = if(this.isAlbum && !this.images.isNullOrEmpty()) {
        this.images[0].link
    }
    else {
        this.link
    }
    return imageUrl.replace("mp4", "jpg")
}

val Post.dateFormatter: SimpleDateFormat
    get() = SimpleDateFormat("dd/MM/yy", Locale.getDefault())