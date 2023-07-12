package com.example.newsapp.ui.news

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.Constants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.databinding.FragmentNewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {
    companion object {
        fun getInstance(source: SourcesItem): NewsFragment {
            val sourceNewsFragment = NewsFragment()
            sourceNewsFragment.source = source
            return sourceNewsFragment
        }
    }

    lateinit var source: SourcesItem
    val adapter = NewsAdapter(null)
    lateinit var binding: FragmentNewsBinding
    var flag = false
    var onItemNewsListener: OnItemNewsListener? = null

    interface OnItemNewsListener {
        fun onNewsClick(article: ArticlesItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("NFCREATEView", "NFCREATEView")
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("NFVCREAT", "NFVCREAT")
        binding.newsRecycler.adapter = adapter
        if (!flag) {
            loadNews()
        }
        adapter.onItemClickListener = object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(article: ArticlesItem) {
                onItemNewsListener?.let {
                    it.onNewsClick(article)
                }
            }
        }
    }

    private fun loadNews() {
        showLoadingLayout()
        // call news api
        ApiManager.getApis()
            .getNews(Constants.apiKey, source.id)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    // ProgressBar is Gone
                    binding.loadingIndicator.isVisible = false

                    // response Success or not
                    if (response.isSuccessful) {
                        adapter.changeData(response.body()?.articles)
                    } else {
                        // message error from json to sourceResponse object
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                            response.errorBody()?.string(),
                            NewsResponse::class.java
                        )
                        showErrorLayout(errorResponse.message)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    // ProgressBar is Gone & show Error Layout
                    binding.loadingIndicator.isVisible = false
                    showErrorLayout(t.localizedMessage)
                }

            })
    }


    private fun showErrorLayout(message: String?) {
        binding.errorLayout.isVisible = true
        binding.errorMessage.text = message
    }

    private fun showLoadingLayout() {
        binding.loadingIndicator.isVisible = true
        binding.errorLayout.isVisible = false
    }


    /////////////////// Test Life Cycle ///////////////////
    override fun onStart() {
        super.onStart()
        Log.e("NFSTART", "NFSTART")
    }

    override fun onResume() {
        super.onResume()
        Log.e("NFResume", "NFResume")
        flag = false
    }

    override fun onPause() {
        super.onPause()
        Log.e("NFPAUSE", "NFPAUSE")
    }

    override fun onStop() {
        super.onStop()
        Log.e("NFSTOP", "NFSTOP")
        flag = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("NFAttach", "NFAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("NFDetach", "NFDetach")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("NFDestroy", "NFDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("NFDestroyView", "NFDestroyView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("NFCreate", "NFCreate")
    }

}