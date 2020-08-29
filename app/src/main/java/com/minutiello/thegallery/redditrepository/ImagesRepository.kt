package com.minutiello.thegallery.redditrepository

interface ImagesRepository {
    fun getImages(keyword: String): List<RedditImage>
}

class ImagesRepositoryFactory {
    fun getImagesRepository(): ImagesRepository {
        return ImagesRepositoryImpl()
    }
}

private class ImagesRepositoryImpl(private val service: RedditService = RedditServiceFactory().getRedditService()) :
    ImagesRepository {


    override fun getImages(keyword: String): List<RedditImage> {
        return service.getImages(keyword).execute().body()?.getImages()
            ?.filter { redditImage: RedditImage ->
                redditImage.thumbUrl?.endsWith(".jpg") ?: false
                        && redditImage.fullUrl?.endsWith(".jpg") ?: false
            } ?: emptyList()
    }

}