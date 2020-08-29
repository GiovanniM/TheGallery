package com.minutiello.thegallery

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditService {

    @GET("r/{keyword}/top.json")
    fun getImages(@Path("keyword") keyword: String): Call<RedditResponse>
}