package com.example.android.codelabs.paging.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtctl.codingchallenge.imagebrowser.model.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos : List<Post>)

    @Query("SELECT * FROM posts WHERE " +
        "title LIKE :queryString " +
        "ORDER BY datetime DESC, title ASC")
    fun postsByName(queryString : String) : PagingSource<Int, Post>

    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}