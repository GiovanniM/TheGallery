package com.minutiello.thegallery.details

import com.minutiello.thegallery.redditrepository.ImagesRepository
import com.minutiello.thegallery.redditrepository.RedditImage

interface DetailsUseCase {
    fun getImage(id: String): RedditImage
    fun changeFavourite(id: String)
}

class DetailsUseCaseImpl(private val imagesRepository: ImagesRepository) :
    DetailsUseCase {

    override fun getImage(id: String): RedditImage {
        return imagesRepository.getImage(id)
    }

    override fun changeFavourite(id: String) {
        imagesRepository.changeFavourite(id)
    }

}