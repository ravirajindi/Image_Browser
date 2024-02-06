package com.thoughtctl.codingchallenge.imagebrowser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.thoughtctl.codingchallenge.imagebrowser.R
import com.thoughtctl.codingchallenge.imagebrowser.databinding.PostsLoadStateFooterViewItemBinding

class PostsLoadStateViewHolder(
    private val binding: PostsLoadStateFooterViewItemBinding,
    retry : () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if(loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.errorMsg.isVisible = loadState is LoadState.Error
        binding.retryButton.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent : ViewGroup, retry: () -> Unit) : PostsLoadStateViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.posts_load_state_footer_view_item, parent, false)
            val binding = PostsLoadStateFooterViewItemBinding.bind(view)
            return PostsLoadStateViewHolder(binding, retry)
        }
    }
}