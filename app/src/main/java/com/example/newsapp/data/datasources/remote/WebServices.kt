package com.example.newsapp.data.datasources.remote

import com.example.newsapp.data.datasources.model.NewsResponse
import com.example.newsapp.data.datasources.model.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    suspend fun getSourcesByCategory(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String?
    ): SourcesResponse

    @GET("v2/everything")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("sources") source: String?
    ): NewsResponse
}