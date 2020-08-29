package com.minutiello.thegallery.imagesrepository

import com.minutiello.thegallery.datamodel.RedditResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class RedditServiceFactory {
    fun getRedditService(): RedditService {
        return Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(RedditService::class.java)
    }
}

interface RedditService {

    @GET("r/{keyword}/top.json")
    fun getImages(@Path("keyword") keyword: String): Call<RedditResponse>

    @GET("r/{keyword}/top.json")
    fun getRawResponse(@Path("keyword") keyword: String): Call<ResponseBody>
}