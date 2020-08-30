package com.minutiello.thegallery.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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

    fun getRedditImageLiveData(id: String): LiveData<RedditImage> {
        return detailsUseCase.getRedditImageLiveData(id)
    }

    fun changeFavourite(id: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            detailsUseCase.changeFavourite(id)
        }
    }

}