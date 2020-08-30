package com.minutiello.thegallery.redditrepository

import android.content.Context
import com.bumptech.glide.Glide

class ImageCacheServiceFactory {

    fun newInstance(context: Context): ImageCacheService {
        return ImageCacheService(context.applicationContext)
    }

}

class ImageCacheService(private val context: Context) {

    fun downloadImages(images: List<RedditImage>) {
        images.forEach {
            Glide.with(context)
                .downloadOnly()
                .load(it.fullUrl)
                .submit()
            Glide.with(context)
                .downloadOnly()
                .load(it.thumbUrl)
                .submit()
        }
    }

}