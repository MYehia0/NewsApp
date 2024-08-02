package com.example.newsapp.ui.news_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.data.datasources.model.ArticlesItem
import com.example.newsapp.databinding.FragmentNewsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {
    companion object {
        fun getInstance(article: ArticlesItem): NewsDetailsFragment {
            val newsDetailsFragment = NewsDetailsFragment()
            newsDetailsFragment.article = article
            return newsDetailsFragment
        }
    }

    lateinit var binding: FragmentNewsDetailsBinding
    lateinit var article: ArticlesItem
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.news = article
        binding.invalidateAll()
    }


    var onStartNewsDetailsListener: OnStartNewsDetailsListener? = null
    interface OnStartNewsDetailsListener {
        fun onStartNewsDetails()
    }

    override fun onStart() {
        super.onStart()
        onStartNewsDetailsListener?.let { it.onStartNewsDetails() }
    }
}