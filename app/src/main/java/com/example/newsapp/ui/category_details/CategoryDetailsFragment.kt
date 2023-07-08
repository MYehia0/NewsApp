package com.example.newsapp.ui.category_details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.Constants
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.databinding.FragmentCategoryDetailsBinding
import com.example.newsapp.ui.category.Category
import com.example.newsapp.ui.news.NewsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsFragment : Fragment() {
    companion object {
        fun getInstance(category: Category): CategoryDetailsFragment {
            val categoryDetailsFragment = CategoryDetailsFragment()
            categoryDetailsFragment.category = category
            return categoryDetailsFragment
        }
    }

    var category: Category? = null
    var tabIndex: Int = 0
    lateinit var binding: FragmentCategoryDetailsBinding
    var onNewsDetailsListener: OnNewsDetailsListener? = null

    interface OnNewsDetailsListener {
        fun onNewsDetailsClick(article: ArticlesItem)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("CDCREATEView", "CDCREATEView")
        binding = FragmentCategoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("CDVCREAT", "CDVCREAT")
        loadSources()
    }

    private fun changeNewsFragment(source: SourcesItem) {
        val newsFragment = NewsFragment.getInstance(source)
        newsFragment.onItemNewsListener = object : NewsFragment.OnItemNewsListener {
            override fun onNewsClick(article: ArticlesItem) {
                onNewsDetailsListener?.let {
                    it.onNewsDetailsClick(article)
                }
            }
        }
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, newsFragment)
            .commit()
    }

    private fun loadSources() {

        showLoadingLayout()

        // call news api
        ApiManager.getApis()
            .getSourcesByCategory(Constants.apiKey, category?.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    // ProgressBar is Gone
                    binding.loadingIndicator.isVisible = false

                    // response Success or not
                    if (response.isSuccessful) {
                        bindSourcesInTabs(response.body()?.sources)
                    } else {
                        // message error from json to sourceResponse object
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                            response.errorBody()?.string(),
                            SourcesResponse::class.java
                        )
                        showErrorLayout(errorResponse.message)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    // ProgressBar is Gone & show Error Layout
                    binding.loadingIndicator.isVisible = false
                    showErrorLayout(t.localizedMessage)
                }

            })
    }

    private fun bindSourcesInTabs(sourcesList: List<SourcesItem?>?) {
        sourcesList?.forEach {
            val tab = binding.tabLayout.newTab()
            tab.text = it?.name
            tab.tag = it
            binding.tabLayout.addTab(tab)
            val layoutParams = LinearLayout.LayoutParams(tab.view.layoutParams)
            layoutParams.setMargins(20, 15, 20, 15)
            tab.view.layoutParams = layoutParams
        }

        binding.tabLayout
            .addOnTabSelectedListener(object : OnTabSelectedListener {

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    tabIndex = tab?.position!!
//                    binding.tabLayout.selectTab(tab)
                    if (tabIndex == 0 && !flag) {
                        val source = tab?.tag as SourcesItem
                        changeNewsFragment(source)
                    }
                    flag = false
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabIndex = tab?.position!!
                    if (!flag) {
                        val source = tab?.tag as SourcesItem
                        changeNewsFragment(source)
                    }
                    flag = false
                    Log.e("onTabSelected", "onTabSelected")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }
            })
        binding.tabLayout.getTabAt(tabIndex)?.select()
    }

    private fun showErrorLayout(message: String?) {
        binding.errorLayout.isVisible = true
        binding.errorMessage.text = message
    }

    private fun showLoadingLayout() {
        binding.loadingIndicator.isVisible = true
        binding.errorLayout.isVisible = false
    }


    //////////////////////////////////////////////////
    var onStartCategoryDetailsListener: OnStartCategoryDetailsListener? = null

    interface OnStartCategoryDetailsListener {
        fun onStartCategoryDetails(category: Category)
    }

    var flag = false
    override fun onStart() {
        super.onStart()
        Log.e("CDSTART", "CDSTART")
        onStartCategoryDetailsListener?.let {
            it.onStartCategoryDetails(category!!)
        }

    }

    override fun onResume() {
        super.onResume()
        Log.e("CDResume", "CDResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("CDPAUSE", "CDPAUSE")
    }

    override fun onStop() {
        super.onStop()
        Log.e("CDSTOP", "CDSTOP")
        flag = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("CDAttach", "CDAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("CDDetach", "CDDetach")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("CDDestroy", "CDDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("CDDestroyView", "CDDestroyView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("CDCreate", "CDCreate")
    }


}














