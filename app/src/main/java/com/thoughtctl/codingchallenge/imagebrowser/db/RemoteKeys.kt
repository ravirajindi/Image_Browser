package com.example.android.codelabs.paging.db

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val postId : String,
    val prevKey : Int?,
    val nextKey : Int?,
)