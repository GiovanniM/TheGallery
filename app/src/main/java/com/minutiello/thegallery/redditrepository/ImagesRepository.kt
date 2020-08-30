package com.minutiello.thegallery.redditrepository

import android.content.Context
import java.lang.Exception

interface ImagesRepository {
    fun getImages(keyword: String): List<RedditImage>
}

class ImagesRepositoryFactory {
    fun getImagesRepository(context: Context, service: RedditService): ImagesRepository {
        return ImagesRepositoryImpl(context, service)
    }
}

private class ImagesRepositoryImpl(
    private val context: Context,
    private val service: RedditService
) :
    ImagesRepository {

    override fun getImages(keyword: String): List<RedditImage> {
        val images = getImagesFromService(keyword)
        return if (images.isEmpty()) {
            getImagesFromDb(keyword)
        } else {
            insertImagesIntoDb(keyword, images)
            images
        }
    }

    private fun getImagesFromService(keyword: String): List<RedditImage> {
        return try {
        return service.getImages(keyword).execute().body()?.getImages()
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

    private fun insertImagesIntoDb(keyword: String, images: List<RedditImage>) {
        AppDatabaseFactory.getInstance(context).redditImageDao().insertAll(*images.toTypedArray())
    }

    private fun getImagesFromDb(keyword: String): List<RedditImage> {
        return AppDatabaseFactory.getInstance(context).redditImageDao().getImages(keyword)
    }

}