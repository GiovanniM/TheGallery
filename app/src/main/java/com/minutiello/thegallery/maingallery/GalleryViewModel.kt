package com.minutiello.thegallery.maingallery

import androidx.lifecycle.*
import com.minutiello.thegallery.datamodel.RedditImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val galleryUseCase: GalleryUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        GalleryViewModel(galleryUseCase) as T
}

class GalleryViewModel(private val galleryUseCase: GalleryUseCase) : ViewModel() {

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
        if (keyword.isNullOrEmpty()) {
            searchJob?.cancel()
            _imagesLiveData.postValue(
                GalleryUIModel(
                    searching = false,
                    query = null,
                    images = emptyList()
                )
            )
        } else {
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
                }
            }
        }
    }

}