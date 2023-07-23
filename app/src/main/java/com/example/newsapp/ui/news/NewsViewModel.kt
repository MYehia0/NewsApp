package com.example.newsapp.ui.news

import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Constants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesItem
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    val articlesList = MutableLiveData<List<ArticlesItem?>>()


    fun loadNews(sourceID:String) {
        showLoadingLayout.value = true
        // call news api
        ApiManager.getApis()
            .getNews(Constants.apiKey, sourceID)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    // ProgressBar is Gone
                    showLoadingLayout.value = false

                    // response Success or not
                    if (response.isSuccessful) {
                        articlesList.value = response.body()?.articles!!
                    } else {
                        // message error from json to sourceResponse object
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                            response.errorBody()?.string(),
                            NewsResponse::class.java
                        )
                        showErrorLayout.value = errorResponse.message?:""
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    // ProgressBar is Gone & show Error Layout
                    showLoadingLayout.value = false
                    showErrorLayout.value = (t.localizedMessage)
                }

            })
    }

}