package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.category.Category
import com.example.newsapp.ui.category.CategoryFragment
import com.example.newsapp.ui.category_details.CategoryDetailsFragment
import com.example.newsapp.ui.news_details.NewsDetailsFragment
import com.example.newsapp.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity(),
    CategoryFragment.OnCategoryListener,
    CategoryDetailsFragment.OnNewsDetailsListener,
    CategoryDetailsFragment.OnStartCategoryDetailsListener,
    CategoryFragment.OnStartCategoryListener {


    lateinit var binding: ActivityMainBinding
    val categoryFragment = CategoryFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("MACREATE", "MACREATE")


        // hamburger icon in app bar
        showHamburgerIcon()
        // side menu item click
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.category_item -> {
                    showCategoryFragment()
                }
                R.id.settings_item -> {
                    showSettingsFragment()
                }
            }
            binding.root.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        // begin Category Fragment
        showCategoryFragment()

    }

    private fun showHamburgerIcon() {
        val toggle = ActionBarDrawerToggle(
            this, binding.root, binding.toolbar, R.string.navigation_open, R.string.navigation_close
        )
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun showCategoryFragment() {
        categoryFragment.onCategoryListener = this
        categoryFragment.onStartCategoryListener = this
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, categoryFragment)
            .commit()

    }

    private fun showSettingsFragment() {
        binding.textToolbar.text = resources.getText(R.string.settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, SettingsFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onCategoryClick(category: Category) {
        showCategoryDetailsFragment(category)
    }

    fun showCategoryDetailsFragment(category: Category) {
        // begin Category Details Fragment
        val categoryDetailsFragment = CategoryDetailsFragment.getInstance(category)
        categoryDetailsFragment.onNewsDetailsListener = this
        categoryDetailsFragment.onStartCategoryDetailsListener = this
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, categoryDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNewsDetailsClick(article: ArticlesItem) {
        binding.textToolbar.text = resources.getText(R.string.news_content)
        showNewsDetailsFragment(article)
    }

    private fun showNewsDetailsFragment(article: ArticlesItem) {
        // begin Category Details Fragment
        val newsDetailsFragment = NewsDetailsFragment.getInstance(article)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, newsDetailsFragment)
            .addToBackStack(null)
            .commit()
    }


    ////////////////////////////////////
    override fun onStartCategoryDetails(category: Category) {
        binding.textToolbar.text = category.name
    }

    override fun onStartCategory() {
        binding.textToolbar.text = resources.getText(R.string.news)
    }

    override fun onStart() {
        super.onStart()
        Log.e("MASTART", "MASTART")
    }

    override fun onResume() {
        super.onResume()
        Log.e("MAResume", "MAResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("MAPAUSE", "MAPAUSE")
    }

    override fun onStop() {
        super.onStop()
        Log.e("MASTOP", "MASTOP")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("MARE", "MARE")
    }


}