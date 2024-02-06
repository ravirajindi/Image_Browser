package com.example.android.codelabs.paging.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.thoughtctl.codingchallenge.imagebrowser.model.Image
import com.thoughtctl.codingchallenge.imagebrowser.model.Post

@Database(
    entities = [
        Post::class,
        RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class ImageBrowserDatabase : RoomDatabase() {

    abstract fun postsDao() : PostDao
    abstract fun remoteKeysDao() : RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE : ImageBrowserDatabase? = null

        fun getInstance(context : Context) : ImageBrowserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(context.applicationContext, ImageBrowserDatabase::class.java, "ImageBrowser.db")
                .build()
    }
}

class TypeConvertor {
    @TypeConverter
    fun fromPostImageList(value : List<Image>?) : String? {
        return value?.joinToString(separator = ",") { it.link }
    }

    @TypeConverter
    fun toPostImageList(value : String?) : List<Image>? {
        return value?.split(",")?.map { Image(id = "", type = "", link = it) }
    }
}