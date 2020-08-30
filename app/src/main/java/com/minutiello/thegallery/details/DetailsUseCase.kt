package com.minutiello.thegallery.details

import androidx.lifecycle.LiveData
import com.minutiello.thegallery.redditrepository.ImagesRepository
import com.minutiello.thegallery.redditrepository.RedditImage

interface DetailsUseCase {
    fun getRedditImageLiveData(id: String): LiveData<RedditImage>
    fun changeFavourite(id: String)
}

class DetailsUseCaseImpl(private val imagesRepository: ImagesRepository) :
    DetailsUseCase {

    override fun getRedditImageLiveData(id: String): LiveData<RedditImage> {
        return imagesRepository.getRedditImageLiveData(id)
    }

    override fun changeFavourite(id: String) {
        imagesRepository.changeFavourite(id)
    }

}