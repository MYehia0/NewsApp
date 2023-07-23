package com.example.newsapp.ui.news

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.api.model.SourcesItem
import com.example.newsapp.databinding.FragmentNewsBinding

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
    lateinit var articlesList: List<ArticlesItem?>
    var flag = false
    var onItemNewsListener: OnItemNewsListener? = null

    interface OnItemNewsListener {
        fun onNewsClick(article: ArticlesItem)
    }

    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("NFCreate", "NFCreate")
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
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
        binding.tryAgain.setOnClickListener {
            viewModel.loadNews(source.id ?: "")
            Log.e("tryAgain", "tryAgain")
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


}