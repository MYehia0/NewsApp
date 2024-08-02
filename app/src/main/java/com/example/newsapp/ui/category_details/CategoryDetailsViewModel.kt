package com.example.newsapp.ui.category_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.datasources.model.SourcesItem
import com.example.newsapp.data.datasources.model.SourcesResponse
import com.example.newsapp.domain.usecases.source.GetSourcesInteractor
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsViewModel @Inject constructor(private val getSourcesInteractor: GetSourcesInteractor) :
    ViewModel() {
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    val sourcesList = MutableLiveData<List<SourcesItem?>>()

    fun loadSources(categoryId: String) {
        // call news api
        viewModelScope.launch {
            showLoadingLayout.postValue(true)
            try {
                // ProgressBar is Gone
                val response = getSourcesInteractor(categoryId)
                sourcesList.postValue(response!!)
                showLoadingLayout.postValue(false)
            } catch (t: HttpException) {
                // message error from json to sourceResponse object
                val gson = Gson()
                val errorResponse = gson.fromJson(
                    t.message,
                    SourcesResponse::class.java
                )
                showErrorLayout.postValue(errorResponse.message!!)
            } catch (ex: Exception) {
                //ProgressBar is Gone & show Error Layout
                showLoadingLayout.postValue(false)
                showErrorLayout.postValue(ex.localizedMessage)
            }
        }
    }
}