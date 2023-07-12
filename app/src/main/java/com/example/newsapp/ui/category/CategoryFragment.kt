package com.example.newsapp.ui.category

import android.os.Bundle
import android.util.Log
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
        Log.e("CFCREATEView", "CFCREATEView")
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("CFVCREAT", "CFVCREAT")
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

    /////////////////// Test Life Cycle ///////////////////
    override fun onStart() {
        super.onStart()
        Log.e("CFSTART", "CFSTART")
        onStartCategoryListener?.let { it.onStartCategory() }
    }

    override fun onResume() {
        super.onResume()
        Log.e("CFResume", "CFResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("CFPAUSE", "CFPAUSE")
    }

    override fun onStop() {
        super.onStop()
        Log.e("CFSTOP", "CFSTOP")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("CFDestroyView", "CFDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("CFDestroy", "CFDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("CFCreate", "CFCreate")
    }

}