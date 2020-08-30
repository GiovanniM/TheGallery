package com.minutiello.thegallery.maingallery

import com.minutiello.thegallery.redditrepository.ImagesRepository
import com.minutiello.thegallery.redditrepository.RedditImage

enum class UseCase {
    FAVOURITES,
    SEARCH
}

interface GalleryUseCase {
    fun getImages(keyword: String?): List<RedditImage>
}

class GalleryUseCaseFactory {

    fun getGalleryUseCase(useCase: UseCase, imagesRepository: ImagesRepository): GalleryUseCase {
        return when (useCase) {
            UseCase.FAVOURITES -> GalleryFavouritesUseCase(imagesRepository)
            else -> GallerySearchUseCase(imagesRepository)
        }
    }
}

private class GallerySearchUseCase(private val imagesRepository: ImagesRepository) :
    GalleryUseCase {
    override fun getImages(keyword: String?): List<RedditImage> {
        return imagesRepository.getImages(keyword)
    }
}

private class GalleryFavouritesUseCase(private val imagesRepository: ImagesRepository) :
    GalleryUseCase {
    override fun getImages(keyword: String?): List<RedditImage> {
        return imagesRepository.getFavourites()
    }
}