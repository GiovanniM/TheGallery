package com.minutiello.thegallery.maingallery

import com.minutiello.thegallery.redditrepository.RedditImage

data class GalleryUIModel(val searching: Boolean, val query: String?, val images: List<RedditImage>)