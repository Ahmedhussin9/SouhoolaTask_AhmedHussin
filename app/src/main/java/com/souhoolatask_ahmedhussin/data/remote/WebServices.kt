package com.souhoolatask_ahmedhussin.data.remote

import com.souhoolatask_ahmedhussin.data.reponse.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    companion object {
        const val BASE_URL = "https://newsapi.org/"
    }

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize:Int,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("language") language: String = "en",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}