package com.thoughtctl.codingchallenge.imagebrowser.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.model.Post
import com.thoughtctl.codingchallenge.imagebrowser.model.getImageUrlForPost

/**
 * View Holder for a [Post] RecyclerView list item.
 */
class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.post_title)
    private val thumbnail : ImageView = view.findViewById(R.id.thumbnail)

    private var post : Post? = null

    fun bind(post: Post?) {
        if (post == null) {
            val resources = itemView.resources
            title.text = resources.getString(R.string.loading)
        } else {
            showPostData(post)
        }
    }

    private fun showPostData(post: Post) {
        this.post = post
        title.text = post.title
        thumbnail.load(post.getImageUrlForPost()) {
            crossfade(true)
            placeholder(R.drawable.loading_img)
            transformations(CircleCropTransformation())
        }
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_view_item, parent, false)
            return PostViewHolder(view)
        }
    }
}
