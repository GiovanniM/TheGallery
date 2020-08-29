package com.minutiello.thegallery.maingallery

import androidx.lifecycle.*
import com.minutiello.thegallery.datamodel.RedditImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val galleryUseCase: GalleryUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        GalleryViewModel(galleryUseCase) as T
}

class GalleryViewModel(private val galleryUseCase: GalleryUseCase) : ViewModel() {

    private val _imagesLiveData = MutableLiveData<List<RedditImage>>()
    val imagesLiveData = _imagesLiveData as LiveData<List<RedditImage>>

    fun getImages(keyword: String?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            _imagesLiveData.postValue(galleryUseCase.getImages(keyword))
        }
    }

}