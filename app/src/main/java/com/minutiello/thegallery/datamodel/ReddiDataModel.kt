package com.minutiello.thegallery.datamodel

import com.squareup.moshi.Json

data class RedditResponse(@field:Json(name = "data") val data: RedditData) {
    fun getImages(): List<RedditImage> {
        return data.children.map { redditChild: RedditChild ->
            RedditImage(redditChild.image.thumbUrl, redditChild.image.fullUrl)
        }
    }
}

data class RedditData(@field:Json(name = "children") val children: List<RedditChild>)

data class RedditChild(@field:Json(name = "data") val image: RedditImage)

data class RedditImage(
    @field:Json(name = "thumbnail") val thumbUrl: String,
    @field:Json(name = "url") val fullUrl: String
)
