package com.example.newsapp.ui.category

import com.example.newsapp.R

data class Category(
    val id: String,
    val name: Int,
    val imageID: Int,
    val colorID: Int
) {
    companion object {
        fun getCategory(): List<Category> {
            return listOf(
                // sports technology health business entertainment science
                Category("sports", R.string.sports, R.drawable.ball, R.color.red),
                Category(
                    "entertainment",
                    R.string.entertainment,
                    R.drawable.entertainment,
                    R.color.blue_dark
                ),
                Category("health", R.string.health, R.drawable.health, R.color.pink),
                Category("business", R.string.business, R.drawable.bussines, R.color.yellow_dark),
                Category("technology", R.string.technology, R.drawable.environment, R.color.blue),
                Category("science", R.string.science, R.drawable.science, R.color.yellow)
            )
        }
    }
}
