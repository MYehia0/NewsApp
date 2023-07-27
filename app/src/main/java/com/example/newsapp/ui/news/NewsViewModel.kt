package com.example.newsapp.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Constants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.api.model.SourcesResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    val articlesList = MutableLiveData<List<ArticlesItem?>>()


    fun loadNews(sourceID:String) {
        // call news api
        viewModelScope.launch {
            showLoadingLayout.postValue(true)
            try {
                // ProgressBar is Gone
                val response = ApiManager.getApis()
                    .getNews(Constants.apiKey, sourceID)
                if (response.status == "error") {
                    // message error from json to sourceResponse object
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        response.message,
                        SourcesResponse::class.java
                    )
                    showErrorLayout.postValue(errorResponse.message!!)
                } else {
                    articlesList.postValue(response.articles!!)
                }
                showLoadingLayout.postValue(false)
                Log.e("Bind", "")
            } catch (ex: Exception) {
                //ProgressBar is Gone & show Error Layout
                showLoadingLayout.postValue(false)
                showErrorLayout.postValue(ex.localizedMessage)
            }
        }

//            .enqueue(object : Callback<NewsResponse> {
//                override fun onResponse(
//                    call: Call<NewsResponse>,
//                    response: Response<NewsResponse>
//                ) {
//                    // ProgressBar is Gone
//                    showLoadingLayout.value = false
//
//                    // response Success or not
//                    if (response.isSuccessful) {
//                        articlesList.value = response.body()?.articles!!
//                    } else {
//                        // message error from json to sourceResponse object
//                        val gson = Gson()
//                        val errorResponse = gson.fromJson(
//                            response.errorBody()?.string(),
//                            NewsResponse::class.java
//                        )
//                        showErrorLayout.value = errorResponse.message?:""
//                    }
//                }
//
//                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                    // ProgressBar is Gone & show Error Layout
//                    showLoadingLayout.value = false
//                    showErrorLayout.value = (t.localizedMessage)
//                }
//
//            })
    }

}