package com.example.glovoorder.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:loadImageFromUrl")
fun  loadImageFromUrl(view:ImageView, url: String?){
    url?.let {
        Glide.with(view.context)
            .load(it)
            .into(view)
    }
}