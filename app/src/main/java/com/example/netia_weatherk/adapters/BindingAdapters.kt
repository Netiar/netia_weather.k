package com.example.netia_weatherk.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl

private val HEADERS = mapOf("User-Agent" to "me")

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(context)
            .load(GlideUrl(it) { HEADERS })
            .into(this)
    }
}
