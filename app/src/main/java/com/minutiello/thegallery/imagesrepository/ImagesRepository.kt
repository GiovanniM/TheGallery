package com.minutiello.thegallery.imagesrepository

import com.minutiello.thegallery.datamodel.RedditImage

interface ImagesRepository {
    fun getImages(keyword: String?): List<RedditImage>
}

class ImagesRepositoryFactory {
    fun getImagesRepository(): ImagesRepository {
        return ImagesRepositoryImpl()
    }
}

private class ImagesRepositoryImpl(private val service: RedditService = RedditServiceFactory().getRedditService()) :
    ImagesRepository {


    override fun getImages(keyword: String?): List<RedditImage> {
        return if (keyword.isNullOrEmpty()) {
            emptyList()
        } else {
            service.getImages(keyword).execute().body()?.getImages()
                ?.filter { redditImage: RedditImage ->
                    redditImage.thumbUrl.endsWith(".jpg") and redditImage.fullUrl.endsWith(".jpg")
                } ?: emptyList()
        }
    }

}