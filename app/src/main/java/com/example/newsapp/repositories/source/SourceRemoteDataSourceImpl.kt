package com.example.newsapp.repositories.source

import com.example.newsapp.Constants
import com.example.newsapp.api.WebServices
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.repositoriesContract.source.SourceRemoteDataSource

class SourceRemoteDataSourceImpl(private val webServices: WebServices) : SourceRemoteDataSource {
    override suspend fun getSourcesByCategory(categoryID: String): List<SourcesItem?>? {
        return webServices.getSourcesByCategory(Constants.apiKey, categoryID).sources
    }
}