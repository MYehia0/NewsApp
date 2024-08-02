package com.example.newsapp.data.repositories.news

import com.example.newsapp.data.datasources.model.ArticlesItem
import com.example.newsapp.data.datasources.remote.Constants
import com.example.newsapp.data.datasources.remote.WebServices
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val webServices: WebServices) {
    suspend fun getNews(sourceID: String): List<ArticlesItem?>? {
        return webServices.getNews(Constants.apiKey, sourceID).articles
    }
}