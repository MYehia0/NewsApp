package com.example.newsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    companion object {
        private var retrofit: Retrofit? = null

        @Synchronized
        private fun getInstance(): Retrofit {
            if (retrofit == null) {

                // loggingInterceptor
                val loggingInterceptor = HttpLoggingInterceptor {
                    Log.e("api", it)
                }
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                // build retrofit object
                retrofit = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .client(okHttpClient) // add Interceptor
                    .addConverterFactory(GsonConverterFactory.create()) // add Gson Converter
                    .build()
            }
            return retrofit!!
        }

        // create WebServices object
        fun getApis(): WebServices {
            return getInstance().create(WebServices::class.java)
        }

    }
}