package com.sample.skilltest.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class ImageUtils {
    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(
            view: ImageView,
            url: String
        ) { // This methods should not have any return type, = declaration would make it return that object declaration.
            if (url.trim().isNotEmpty())
                Glide.with(view.context).load(url).override(106, 160).into(view)
        }
    }
}