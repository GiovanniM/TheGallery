package com.minutiello.thegallery.redditrepository

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RedditImageDao {

    @Query("SELECT * FROM redditimage WHERE keyword LIKE :keyword")
    fun getImages(keyword: String): List<RedditImage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg images: RedditImage)

    @Query("SELECT * FROM redditimage WHERE id LIKE :id")
    fun getRedditImageLiveData(id: String): LiveData<RedditImage>

    @Query("SELECT * FROM redditimage WHERE id LIKE :id")
    fun getRedditImage(id: String): RedditImage

    @Update
    fun updateRedditImage(vararg images: RedditImage)

    @Transaction
    fun changeFavourite(id: String) {
        val redditImage = getRedditImage(id)
        updateRedditImage(redditImage.copy(favourite = !(redditImage.favourite == true)))
    }
}