package com.minutiello.thegallery.redditrepository

import androidx.room.Entity
import com.squareup.moshi.Json
import java.io.Serializable

data class RedditResponse(@field:Json(name = "data") val data: RedditData) : Serializable {
    fun getImages(): List<RedditImage> {
        return data.children.map { redditChild: RedditChild ->
            redditChild.image
        }
    }
}

data class RedditData(@field:Json(name = "children") val children: List<RedditChild>) : Serializable

data class RedditChild(@field:Json(name = "data") val image: RedditImage) : Serializable

@Entity(primaryKeys = ["id"], tableName = "redditimage")
data class RedditImage(
    @field:Json(name = "id") val id: String,
    val keyword: String = "",
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "created") val created: Long?,
    @field:Json(name = "author") val author: String?,
    @field:Json(name = "score") val score: Long?,
    @field:Json(name = "thumbnail") val thumbUrl: String?,
    @field:Json(name = "url") val fullUrl: String?
) : Serializable