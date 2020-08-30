package com.minutiello.thegallery.redditrepository

import androidx.lifecycle.LiveData
import java.lang.Exception

interface ImagesRepository {
    fun getImages(keyword: String?): List<RedditImage>
    fun getFavourites(): List<RedditImage>
    fun getRedditImageLiveData(id: String): LiveData<RedditImage>
    fun changeFavourite(id: String)
}

class ImagesRepositoryFactory {
    fun getImagesRepository(redditImageDao: RedditImageDao, service: RedditService): ImagesRepository {
        return ImagesRepositoryImpl(redditImageDao, service)
    }
}

private class ImagesRepositoryImpl(
    private val redditImageDao: RedditImageDao,
    private val service: RedditService
) :
    ImagesRepository {

    override fun getImages(keyword: String?): List<RedditImage> {
        if (keyword.isNullOrEmpty()) {
            return emptyList()
        }
        val images = getImagesFromService(keyword)
        return if (images.isEmpty()) {
            getImagesFromDb(keyword)
        } else {
            insertImagesIntoDb(images)
            images
        }
    }

    override fun getFavourites(): List<RedditImage> {
        return redditImageDao.getFavourites()
    }

    override fun getRedditImageLiveData(id: String): LiveData<RedditImage> {
        return redditImageDao.getRedditImageLiveData(id)
    }

    override fun changeFavourite(id: String) {
        redditImageDao.changeFavourite(id)
    }

    private fun getImagesFromService(keyword: String): List<RedditImage> {
        return try {
            service.getImages(keyword).execute().body()?.getImages()
                ?.filter { redditImage: RedditImage ->
                    redditImage.thumbUrl?.endsWith(".jpg") ?: false
                            && redditImage.fullUrl?.endsWith(".jpg") ?: false
                }?.map { redditImage: RedditImage ->
                    redditImage.copy(keyword = keyword)
                } ?: emptyList()
        } catch (exception: Exception) {
            emptyList()
        }
    }

    private fun insertImagesIntoDb(images: List<RedditImage>) {
        redditImageDao.insertAll(*images.toTypedArray())
    }

    private fun getImagesFromDb(keyword: String): List<RedditImage> {
        return redditImageDao.getImages(keyword)
    }

}