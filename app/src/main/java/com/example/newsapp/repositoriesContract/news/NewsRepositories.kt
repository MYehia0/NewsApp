package com.example.newsapp.repositoriesContract.news

import com.example.newsapp.api.model.ArticlesItem

interface NewsRepository {
    suspend fun getNews(sourceID: String): List<ArticlesItem?>?
}

interface NewsRemoteDataSource {
    suspend fun getNews(sourceID: String): List<ArticlesItem?>?
}
