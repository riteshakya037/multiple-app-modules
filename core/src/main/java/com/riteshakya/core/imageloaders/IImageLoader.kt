package com.riteshakya.core.imageloaders

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.engine.GlideException

interface IImageLoader {
    fun loadImage(
            url: String,
            holder: ImageView,
            onLoadSuccess: (Drawable) -> Unit = {},
            onLoadFail: (GlideException) -> Unit = {}
    )
}
