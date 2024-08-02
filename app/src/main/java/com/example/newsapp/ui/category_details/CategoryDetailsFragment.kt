package com.example.newsapp.ui.category_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newsapp.R
import com.example.newsapp.data.datasources.model.ArticlesItem
import com.example.newsapp.data.datasources.model.SourcesItem
import com.example.newsapp.databinding.FragmentCategoryDetailsBinding
import com.example.newsapp.ui.category.Category
import com.example.newsapp.ui.news.NewsFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    var flag = false
    private val viewModel: CategoryDetailsViewModel by viewModels()
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
        binding = FragmentCategoryDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tryAgain.setOnClickListener {
            viewModel.loadSources(category?.id ?: "")
        }
        if (!flag) {
            subscribeToLiveData()
            viewModel.loadSources(category?.id ?: "")
        }
        selectTab()
    }

    private fun subscribeToLiveData() {
        viewModel.showLoadingLayout.observe(viewLifecycleOwner) {
            showLoadingLayout(it)
            if (it) {
                hideErrorLayout()
            }
        }
        viewModel.showErrorLayout.observe(viewLifecycleOwner) {
            showErrorLayout(it)
        }
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

    private fun selectTab() {
        binding.tabLayout
            .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    if (tab?.position == 0 && !flag) {
                        val source = tab.tag as SourcesItem
                        changeNewsFragment(source)
                        Log.e("onReCondition", "1")
                    } else if (tabIndex == tab?.position && !flag) {
                        val source = tab.tag as SourcesItem
                        changeNewsFragment(source)
                        Log.e("onReCondition", "2")
                    }
                    Log.e("onTabReselected", "onTabReselected")
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tabIndex != tab?.position && tab?.position != 0) {
                        val source = tab?.tag as SourcesItem
                        changeNewsFragment(source)
                        tabIndex = tab?.position!!
                        Log.e("onTabCondition", "1")
                    } else if (tab?.position == 0 && !flag) {
                        val source = tab?.tag as SourcesItem
                        changeNewsFragment(source)
                        tabIndex = tab?.position!!
                        Log.e("onTabCondition", "2")
                    }
                    Log.e("onTabSelected", tab?.position.toString() + " ${flag}")
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })
    }

    private fun showErrorLayout(message: String?) {
        binding.errorLayout.isVisible = true
        binding.errorMessage.text = message
    }

    private fun showLoadingLayout(flag: Boolean) {
        binding.loadingIndicator.isVisible = flag
    }

    private fun hideErrorLayout() {
        binding.errorLayout.isVisible = false
    }

    var onStartCategoryDetailsListener: OnStartCategoryDetailsListener? = null

    interface OnStartCategoryDetailsListener {
        fun onStartCategoryDetails(category: Category)
    }

    override fun onStart() {
        super.onStart()
        onStartCategoryDetailsListener?.let {
            it.onStartCategoryDetails(category!!)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(tabIndex))
        flag = false
    }

    override fun onStop() {
        super.onStop()
        flag = true
    }
}
