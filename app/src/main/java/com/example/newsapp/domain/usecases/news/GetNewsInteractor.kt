package com.example.newsapp.domain.usecases.news

import com.example.newsapp.data.repositories.news.NewsRemoteDataSource
import javax.inject.Inject

class GetNewsInteractor @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) {
    suspend operator fun invoke(sourceID: String) = newsRemoteDataSource.getNews(sourceID)
}