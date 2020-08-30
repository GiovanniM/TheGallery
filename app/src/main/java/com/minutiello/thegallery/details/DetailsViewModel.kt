package com.minutiello.thegallery.details

import androidx.lifecycle.*
import com.minutiello.thegallery.redditrepository.RedditImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val detailsUseCase: DetailsUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailsViewModel(detailsUseCase) as T
}

class DetailsViewModel(private val detailsUseCase: DetailsUseCase) : ViewModel() {

    private val _imageLiveData = MutableLiveData<RedditImage>()
    val imageLiveData = _imageLiveData as LiveData<RedditImage>

    fun getImage(id: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            _imageLiveData.postValue(detailsUseCase.getImage(id))
        }
    }

    fun changeFavourite(id: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            detailsUseCase.changeFavourite(id)
            _imageLiveData.postValue(detailsUseCase.getImage(id))
        }
    }

}