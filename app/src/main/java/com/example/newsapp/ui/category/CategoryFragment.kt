package com.example.newsapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    lateinit var adapter: CategoryAdapter
    lateinit var binding: FragmentCategoryBinding
    var onCategoryListener: OnCategoryListener? = null

    interface OnCategoryListener {
        fun onCategoryClick(category: Category)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoryAdapter(Category.getCategory())
        binding.categoryRecycler.adapter = adapter
        adapter.onItemClickListener = object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(postion: Int, item: Category) {
                onCategoryListener?.let {
                    it.onCategoryClick(item)
                }
            }
        }
    }

    var onStartCategoryListener: OnStartCategoryListener? = null
    interface OnStartCategoryListener {
        fun onStartCategory()
    }

    override fun onStart() {
        super.onStart()
        onStartCategoryListener?.let { it.onStartCategory() }
    }
}