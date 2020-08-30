package com.minutiello.thegallery.maingallery

import androidx.lifecycle.*
import com.minutiello.thegallery.redditrepository.ImageCacheService
import kotlinx.coroutines.*

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val galleryUseCase: GalleryUseCase,
    private val imageCacheService: ImageCacheService
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        GalleryViewModel(galleryUseCase, imageCacheService) as T
}

class GalleryViewModel(
    private val galleryUseCase: GalleryUseCase,
    private val imageCacheService: ImageCacheService
) : ViewModel() {

    private var searchJob: Job? = null

    private val _imagesLiveData = MutableLiveData(
        GalleryUIModel(
            searching = false,
            query = null,
            images = emptyList()
        )
    )
    val imagesLiveData = _imagesLiveData as LiveData<GalleryUIModel>

    fun getImages(keyword: String?) {
        searchJob?.cancel()
        _imagesLiveData.postValue(
            GalleryUIModel(
                searching = true,
                query = keyword,
                images = emptyList()
            )
        )
        searchJob = viewModelScope.launch(context = Dispatchers.IO) {
            val images = galleryUseCase.getImages(keyword)
            if (isActive) {
                _imagesLiveData.postValue(
                    GalleryUIModel(
                        searching = false,
                        query = keyword,
                        images = images
                    )
                )
                imageCacheService.downloadImages(images)
            }
        }
    }
}