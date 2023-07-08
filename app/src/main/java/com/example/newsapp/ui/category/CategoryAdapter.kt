package com.example.newsapp.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        holder.binding.apply {
            text.text = item.name
            image.setImageResource(item.imageID)
            container.setCardBackgroundColor(
                ContextCompat.getColor(
                    this.root.context,
                    item.colorID
                )
            )

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

    class CategoryHolder(val binding: ItemCategoryBinding) : ViewHolder(binding.root)
}