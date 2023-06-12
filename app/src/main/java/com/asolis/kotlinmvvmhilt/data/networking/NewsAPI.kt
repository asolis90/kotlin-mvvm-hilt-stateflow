package com.asolis.kotlinmvvmhilt.data.networking

import com.asolis.kotlinmvvmhilt.data.models.DataResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NewsAPI {
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): DataResponse

    companion object Factory {
        var BASE_URL: String = "https://newsapi.org/v2/"
        var API_KEY: String = "<API-KEY>"
        val HTTP_CACHE_FILENAME = "NewsApiHttpCache"
    }
}