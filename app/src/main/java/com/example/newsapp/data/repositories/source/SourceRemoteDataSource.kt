package com.example.newsapp.data.repositories.source

import com.example.newsapp.data.datasources.model.SourcesItem
import com.example.newsapp.data.datasources.remote.Constants
import com.example.newsapp.data.datasources.remote.WebServices
import javax.inject.Inject

class SourceRemoteDataSource @Inject constructor(private val webServices: WebServices) {
    suspend fun getSourcesByCategory(categoryID: String): List<SourcesItem?>? {
        return webServices.getSourcesByCategory(Constants.apiKey, categoryID).sources
    }
}