package com.example.newsapp.repositories.news

import com.example.newsapp.Constants
import com.example.newsapp.api.WebServices
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.repositoriesContract.news.NewsRemoteDataSource

class NewsRemoteDataSourceImpl(private val webServices: WebServices) : NewsRemoteDataSource {
    override suspend fun getNews(sourceID: String): List<ArticlesItem?>? {
        return webServices.getNews(Constants.apiKey, sourceID).articles
    }
}