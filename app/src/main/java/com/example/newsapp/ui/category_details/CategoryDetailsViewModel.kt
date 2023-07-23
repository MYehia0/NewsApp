package com.example.newsapp.ui.category_details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Constants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.api.model.SourcesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsViewModel: ViewModel() {
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    val sourcesList = MutableLiveData<List<SourcesItem?>>()

    fun loadSources(categoryId:String) {
        showLoadingLayout.value = true
        // call news api
        ApiManager.getApis()
            .getSourcesByCategory(Constants.apiKey, categoryId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    // ProgressBar is Gone
                    showLoadingLayout.value = false
                    // response Success or not
                    if (response.isSuccessful) {
                        sourcesList.value = response.body()?.sources!!
                        Log.e("Bind", "")
                    } else {
                        // message error from json to sourceResponse object
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                            response.errorBody()?.string(),
                            SourcesResponse::class.java
                        )
                        showErrorLayout.value = errorResponse.message!!
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    // ProgressBar is Gone & show Error Layout
                    showLoadingLayout.value = false
                    showErrorLayout.value = t.localizedMessage
                }

            })
    }

}