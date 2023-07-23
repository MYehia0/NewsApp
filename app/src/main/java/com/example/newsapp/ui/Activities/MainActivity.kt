package com.example.newsapp.ui.Activities

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.api.model.ArticlesItem
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.category.Category
import com.example.newsapp.ui.category.CategoryFragment
import com.example.newsapp.ui.category_details.CategoryDetailsFragment
import com.example.newsapp.ui.news_details.NewsDetailsFragment
import com.example.newsapp.ui.settings.SettingsFragment
import java.util.*

class MainActivity : AppCompatActivity(),
    CategoryFragment.OnCategoryListener,
    CategoryDetailsFragment.OnNewsDetailsListener,
    CategoryDetailsFragment.OnStartCategoryDetailsListener,
    NewsDetailsFragment.OnStartNewsDetailsListener,
    CategoryFragment.OnStartCategoryListener,
    SettingsFragment.OnLanguageClickListener,
    OnLocaleChangedListener {


    lateinit var binding: ActivityMainBinding
    val categoryFragment = CategoryFragment()
    val settingsFragment = SettingsFragment()
    private val localizationDelegate = LocalizationActivityDelegate(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        Log.e("MACREATE", "MACREATE")


        // hamburger icon in app bar
        showHamburgerIcon()
        // side menu item click
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.category_item -> {
                    for (i in 1..supportFragmentManager.backStackEntryCount) {
                        supportFragmentManager.popBackStack()
                    }
                    showCategoryFragment()
                }
                R.id.settings_item -> {
                    val fragmrnt =
                        supportFragmentManager.findFragmentById(R.id.fragment_container_main)
                    if (fragmrnt !is SettingsFragment) {
                        showSettingsFragment()
                    }
                }
            }
            binding.drawer.closeDrawers()
            return@setNavigationItemSelectedListener true
        }

        // begin Category Fragment
        for (i in 1..supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
        showCategoryFragment()

//        if(Constant.STATE == "Category") {
//            for (i in 1..supportFragmentManager.backStackEntryCount) {
//                supportFragmentManager.popBackStack()
//            }
//            Log.e("onRestoreInstanceState", "Category")
//            showCategoryFragment()
//        }else if(Constant.STATE == "Settings"){
//            Log.e("onRestoreInstanceState", "Settings")
//            showSettingsFragment()
//        }

    }

    private fun showHamburgerIcon() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            binding.toolbar,
            R.string.navigation_open,
            R.string.navigation_close
        )
        binding.drawer.addDrawerListener(toggle)
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
        settingsFragment.onLanguageClickListener = this
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, settingsFragment)
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
        showNewsDetailsFragment(article)
    }

    private fun showNewsDetailsFragment(article: ArticlesItem) {
        // begin Category Details Fragment
        val newsDetailsFragment = NewsDetailsFragment.getInstance(article)
        newsDetailsFragment.onStartNewsDetailsListener = this
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, newsDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onStartCategoryDetails(category: Category) {
        binding.textToolbar.text = getText(category.name)
    }

    override fun onStartCategory() {
        binding.textToolbar.text = resources.getText(R.string.news)
    }

    override fun onStartNewsDetails() {
        binding.textToolbar.text = resources.getText(R.string.news_content)
    }

    override fun onLanguageClick(language: String) {
        if (language.equals("English") || language.equals("الانجليزية")) {
            setLanguage("en")
        } else if (language.equals("Arabic") || language.equals("العربية")) {
            setLanguage("ar")
        }
    }


//    @RequiresApi(Build.VERSION_CODES.N)
//    fun setLocale(language:String){
//        var metrics = resources.displayMetrics
//        var configuration = resources.configuration
//        configuration.locale = Locale(language)
//        resources.updateConfiguration(configuration,metrics)
//    }

    /////////////////// Language Methods ///////////////////
    override fun attachBaseContext(newBase: Context) {
        applyOverrideConfiguration(localizationDelegate.updateConfigurationLocale(newBase))
        super.attachBaseContext(newBase)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(super.getResources())
    }

    fun setLanguage(language: String?) {
        localizationDelegate.setLanguage(this, language!!)
    }

    fun setLanguage(locale: Locale?) {
        localizationDelegate.setLanguage(this, locale!!)
    }

    val currentLanguage: Locale
        get() = localizationDelegate.getLanguage(this)

    override fun onAfterLocaleChanged() {
        Log.e("onAfterLocaleChanged()", "onAfterLocaleChanged()")
        Constants.STATE = "Category"
    }

    override fun onBeforeLocaleChanged() {
        Log.e("onBeforeLocaleChanged", "onBeforeLocaleChanged")
        Constants.STATE = "Settings"
    }

    /////////////////// Test Life Cycle ///////////////////
    override fun onStart() {
        super.onStart()
        Log.e("MASTART", "MASTART")
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

    override fun onResume() {
        super.onResume()
        Log.e("MAResume", "MAResume")
        localizationDelegate.onResume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MADestroy", "MADestroy")

    }

    ///////// back stack /////////
//    override fun onBackPressed() {
//        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_main)
//        if(fragment is CategoryFragment ){
//            finish()
//        }else{
//            super.onBackPressed()
//        }
//
//    }


}