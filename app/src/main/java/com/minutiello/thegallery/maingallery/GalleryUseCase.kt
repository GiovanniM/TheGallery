package com.minutiello.thegallery.maingallery

import com.minutiello.thegallery.redditrepository.ImagesRepository
import com.minutiello.thegallery.redditrepository.RedditImage

interface GalleryUseCase {
    fun getImages(keyword: String): List<RedditImage>
}

class GalleryUseCaseImpl(private val imagesRepository: ImagesRepository) :
    GalleryUseCase {
    override fun getImages(keyword: String): List<RedditImage> {
        return imagesRepository.getImages(keyword)
    }

}