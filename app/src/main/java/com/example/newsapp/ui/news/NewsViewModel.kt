package com.example.newsapp.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.datasources.model.ArticlesItem
import com.example.newsapp.data.datasources.model.SourcesResponse
import com.example.newsapp.domain.usecases.news.GetNewsInteractor
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsInteractor: GetNewsInteractor) :
    ViewModel() {
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    val articlesList = MutableLiveData<List<ArticlesItem?>>()

    fun loadNews(sourceID: String) {
        // call news api
        viewModelScope.launch {
            showLoadingLayout.postValue(true)
            try {
                // ProgressBar is Gone
                val response = getNewsInteractor(sourceID)
                articlesList.postValue(response!!)
                showLoadingLayout.postValue(false)
                Log.e("Bind", "")
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