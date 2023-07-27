package com.example.newsapp.ui.category_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Constants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.api.model.SourcesResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch

class CategoryDetailsViewModel : ViewModel() {
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    val sourcesList = MutableLiveData<List<SourcesItem?>>()

    fun loadSources(categoryId: String) {
        // call news api
        viewModelScope.launch {
            showLoadingLayout.postValue(true)
            try {
                // ProgressBar is Gone
                val response = ApiManager.getApis()
                    .getSourcesByCategory(Constants.apiKey, categoryId)
                if (response.status == "error") {
                    // message error from json to sourceResponse object
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        response.message,
                        SourcesResponse::class.java
                    )
                    showErrorLayout.postValue(errorResponse.message!!)
                } else {
                    sourcesList.postValue(response.sources!!)
                }
                showLoadingLayout.postValue(false)
            } catch (ex: Exception) {
                //ProgressBar is Gone & show Error Layout
                showLoadingLayout.postValue(false)
                showErrorLayout.postValue(ex.localizedMessage)
            }
        }

//            .enqueue(object : Callback<SourcesResponse> {
//                override fun onResponse(
//                    call: Call<SourcesResponse>,
//                    response: Response<SourcesResponse>
//                ) {
//                    // ProgressBar is Gone
//                    showLoadingLayout.value = false
//                    // response Success or not
//                    if (response.isSuccessful) {
//                        sourcesList.value = response.body()?.sources!!
//                        Log.e("Bind", "")
//                    } else {
//                        // message error from json to sourceResponse object
//                        val gson = Gson()
//                        val errorResponse = gson.fromJson(
//                            response.errorBody()?.string(),
//                            SourcesResponse::class.java
//                        )
//                        showErrorLayout.value = errorResponse.message!!
//                    }
//                }
//
//                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
//                    // ProgressBar is Gone & show Error Layout
//                    showLoadingLayout.value = false
//                    showErrorLayout.value = t.localizedMessage
//                }
//
//            })

    }

}