package com.example.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newsapp.data.datasources.model.ArticlesItem
import com.example.newsapp.data.datasources.model.SourcesItem
import com.example.newsapp.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    private val viewModel: NewsViewModel by viewModels()
    lateinit var articlesList: List<ArticlesItem?>
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
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsRecycler.adapter = adapter
        binding.tryAgain.setOnClickListener {
            viewModel.loadNews(source.id ?: "")
        }
        if (!flag) {
            subscribeToLiveData()
            source.id?.let { viewModel.loadNews(it) }
        } else {
            adapter.changeData(articlesList)
        }
        adapter.onItemClickListener = object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(article: ArticlesItem) {
                onItemNewsListener?.let {
                    it.onNewsClick(article)
                }
            }
        }
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
        viewModel.articlesList.observe(viewLifecycleOwner) {
            articlesList = it
            adapter.changeData(articlesList)
        }
    }

    private fun showErrorLayout(message: String?) {
        binding.errorLayout.isVisible = true
        binding.errorMessage.text = message
        binding.newsRecycler.isVisible = false
    }

    private fun showLoadingLayout(flag: Boolean) {
        binding.loadingIndicator.isVisible = flag
    }

    private fun hideErrorLayout() {
        binding.errorLayout.isVisible = false
        binding.newsRecycler.isVisible = true
    }

    override fun onResume() {
        super.onResume()
        flag = false
    }

    override fun onStop() {
        super.onStop()
        flag = true
    }
}