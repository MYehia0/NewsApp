package com.example.newsapp.ui

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.datasources.model.SourcesItem
import com.google.android.material.tabs.TabLayout

@BindingAdapter("imageUrl")
fun loadImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.ic_image)
            .into(imageView)
    }
}

@BindingAdapter("CardBackgroundByID")
fun changeCardBackgroundByID(cardView: CardView,color:Int){
    cardView.setCardBackgroundColor(
        ContextCompat.getColor(
            cardView.context,
            color
        )
    )
}

@BindingAdapter("ImageByID")
fun changeImageByID(imageView: ImageView,imageID:Int){
    imageView.setImageResource(imageID)
}

@BindingAdapter("bindSourcesInTabs")
fun bindSourcesInTabs(tabLayout: TabLayout,sourcesList: List<SourcesItem?>?) {
    sourcesList?.forEach {
        val tab = tabLayout.newTab()
        tab.text = it?.name
        tab.tag = it
        tabLayout.addTab(tab)
        val layoutParams = LinearLayout.LayoutParams(tab.view.layoutParams)
        layoutParams.setMargins(20, 15, 20, 15)
        tab.view.layoutParams = layoutParams
    }
}
