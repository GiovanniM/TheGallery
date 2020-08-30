package com.minutiello.thegallery.redditrepository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RedditImageDao {

    @Query("SELECT * FROM redditimage WHERE keyword LIKE :keyword")
    fun getImages(keyword: String): List<RedditImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg images: RedditImage)
}