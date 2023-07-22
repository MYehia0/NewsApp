package com.example.newsapp.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.newsapp.databinding.ItemCategoryBinding

class CategoryAdapter(val items: List<Category>) : Adapter<CategoryAdapter.CategoryHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    lateinit var binding: ItemCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        var item = items[position]
        holder.Bind(item)
        holder.binding.apply {
            root.setOnClickListener {
                onItemClickListener?.let {
                    it.onItemClick(position, item)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(postion: Int, item: Category)
    }

    class CategoryHolder(val binding: ItemCategoryBinding) : ViewHolder(binding.root) {
        fun Bind(category: Category?) {
            binding.category = category
            binding.executePendingBindings()
        }
    }
}