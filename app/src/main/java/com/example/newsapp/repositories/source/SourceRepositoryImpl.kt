package com.example.newsapp.repositories.source

import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.repositoriesContract.source.SourceRemoteDataSource
import com.example.newsapp.repositoriesContract.source.SourceRepository

class SourceRepositoryImpl(private val sourceRemoteDataSource: SourceRemoteDataSource) :
    SourceRepository {
    override suspend fun getSourcesByCategory(categoryID: String): List<SourcesItem?>? {
        return sourceRemoteDataSource.getSourcesByCategory(categoryID)
    }
}