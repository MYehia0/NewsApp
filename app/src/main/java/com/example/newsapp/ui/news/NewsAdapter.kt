package com.example.newsapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.newsapp.data.datasources.model.ArticlesItem
import com.example.newsapp.databinding.ItemNewsBinding

class NewsAdapter(var items: List<ArticlesItem?>?) : Adapter<NewsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.Bind(item)
        holder.binding.root.setOnClickListener {
            onItemClickListener?.let {
                it.onItemClick(item!!)
            }
        }

    }

    fun changeData(articles: List<ArticlesItem?>?) {
        items = articles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items?.size ?: 0

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(article: ArticlesItem)
    }

    class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun Bind(article: ArticlesItem?) {
            binding.news = article
            binding.executePendingBindings()
        }
    }
}