package com.thoughtctl.codingchallenge.imagebrowser.ui.composable

import android.icu.text.SimpleDateFormat
import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.model.getImageUrlForPost
import java.util.Date
import java.util.Locale

/**
 * Composable to show post as a grid item.
 */
@Composable
fun GridPostCard(
    post : Post, modifier: Modifier = Modifier
) {
    val imageUrl = post.getImageUrlForPost()
    Card(
        modifier = Modifier.fillMaxHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                var isLoading by rememberSaveable {
                    mutableStateOf(true)
                }

                if(isLoading) {
                    CircularProgressIndicator()
                }
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.ic_broken_image),
                    contentDescription = post.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(150.dp),
                    onLoading = {
                        isLoading = true
                    },
                    onSuccess = {
                        isLoading = false
                    },
                    onError = {
                        isLoading = false
                    }
                )
            }
            Column (
                modifier = Modifier.height(150.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = post.title,
                    fontWeight = FontWeight.Bold,
                    minLines = 2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )

                val imageCount = post.imagesCount ?: 0
                Text(
                    text = pluralStringResource(R.plurals.image_count, imageCount, imageCount),
                    modifier = Modifier.padding(4.dp)
                )

                val formatter = SimpleDateFormat("dd/MM/yy hh:mm a", Locale.getDefault())
                val postDate = Date(post.datetime * DateUtils.SECOND_IN_MILLIS)
                Text(
                    text = "Posted on : ${formatter.format(postDate)}",
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
