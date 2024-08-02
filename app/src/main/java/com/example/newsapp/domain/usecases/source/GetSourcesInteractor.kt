package com.example.newsapp.domain.usecases.source

import com.example.newsapp.data.repositories.source.SourceRemoteDataSource
import javax.inject.Inject

class GetSourcesInteractor @Inject constructor(private val sourceRemoteDataSource: SourceRemoteDataSource) {
    suspend operator fun invoke(categoryID: String) =
        sourceRemoteDataSource.getSourcesByCategory(categoryID)
}