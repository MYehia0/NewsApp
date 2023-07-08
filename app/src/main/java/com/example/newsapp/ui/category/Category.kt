package com.example.newsapp.ui.category

import com.example.newsapp.R

data class Category(
    val id: String,
    val name: String,
    val imageID: Int,
    val colorID: Int
) {
    companion object {
        fun getCategory(): List<Category> {
            return listOf(
                // sports technology health business entertainment science
                Category("sports", "Sports", R.drawable.ball, R.color.red),
                Category("technology", "Technology", R.drawable.politics, R.color.blue_dark),
                Category("health", "Health", R.drawable.health, R.color.pink),
                Category("business", "Business", R.drawable.bussines, R.color.yellow_dark),
                Category("entertainment", "Entertainment", R.drawable.environment, R.color.blue),
                Category("science", "Science", R.drawable.science, R.color.yellow)
            )
        }
    }
}
