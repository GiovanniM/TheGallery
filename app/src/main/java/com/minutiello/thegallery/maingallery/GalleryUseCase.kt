package com.minutiello.thegallery.maingallery

import com.minutiello.thegallery.datamodel.RedditImage
import com.minutiello.thegallery.imagesrepository.ImagesRepository
import com.minutiello.thegallery.imagesrepository.ImagesRepositoryFactory

interface GalleryUseCase {
    fun getImages(keyword: String?): List<RedditImage>
}

class GalleryUseCaseImpl(private val imagesRepository: ImagesRepository = ImagesRepositoryFactory().getImagesRepository()) :
    GalleryUseCase {
    override fun getImages(keyword: String?): List<RedditImage> {
        return imagesRepository.getImages(keyword)
    }

}