package com.example.newsapp.ui.category_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.repositories.source.SourceRemoteDataSourceImpl
import com.example.newsapp.repositories.source.SourceRepositoryImpl
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CategoryDetailsViewModel : ViewModel() {
    val showLoadingLayout = MutableLiveData<Boolean>()
    val showErrorLayout = MutableLiveData<String>()
    val sourcesList = MutableLiveData<List<SourcesItem?>>()
    private val apiManager = ApiManager.getApis()
    private val sourceRemoteDataSource = SourceRemoteDataSourceImpl(apiManager)
    private val sourceRepository = SourceRepositoryImpl(sourceRemoteDataSource)

    fun loadSources(categoryId: String) {
        // call news api
        viewModelScope.launch {
            showLoadingLayout.postValue(true)
            try {
                // ProgressBar is Gone
                val response = sourceRepository.getSourcesByCategory(categoryId)
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