package com.example.newsapp.repositoriesContract.source

import com.example.newsapp.api.model.SourcesItem

interface SourceRepository {
    suspend fun getSourcesByCategory(categoryID:String):List<SourcesItem?>?
}
interface SourceRemoteDataSource {
    suspend fun getSourcesByCategory(categoryID:String):List<SourcesItem?>?
}
