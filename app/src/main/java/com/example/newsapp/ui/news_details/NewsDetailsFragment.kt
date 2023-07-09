package com.example.newsapp.ui.news_details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.databinding.FragmentNewsDetailsBinding

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
        Log.e("NDCREATEView", "NDCREATEView")
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("NDVCREAT", "NDVCREAT")
        binding.title.text = article.title
        binding.author.text = article.author
        binding.time.text = article.publishedAt
        binding.contentNews.text = article.description

        Glide.with(binding.root)
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_image)
            .into(binding.image)
    }


    ////////////////////////////////////////////////////////////
    var onStartNewsDetailsListener: OnStartNewsDetailsListener? = null

    interface OnStartNewsDetailsListener {
        fun onStartNewsDetails()
    }

    override fun onStart() {
        super.onStart()
        Log.e("NDSTART", "NDSTART")
        onStartNewsDetailsListener?.let { it.onStartNewsDetails() }
    }

    override fun onStop() {
        super.onStop()
        Log.e("NDSTOP", "NDSTOP")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("NDAttach", "NDAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("NDDetach", "NDDetach")
    }

    override fun onResume() {
        super.onResume()
        Log.e("NDResume", "NDResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("NDPAUSE", "NDPAUSE")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("NDDestroy", "NDDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("NDDestroyView", "NDDestroyView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("NDCreate", "NDCreate")
    }


}