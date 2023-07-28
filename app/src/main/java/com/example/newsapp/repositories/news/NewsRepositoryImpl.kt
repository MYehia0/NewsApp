package com.example.newsapp.repositories.news

import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.repositoriesContract.news.NewsRemoteDataSource
import com.example.newsapp.repositoriesContract.news.NewsRepository

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource) : NewsRepository {
    override suspend fun getNews(sourceID: String): List<ArticlesItem?>? {
        return newsRemoteDataSource.getNews(sourceID)
    }
}