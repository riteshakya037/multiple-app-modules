package com.riteshakya.teacher.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import javax.inject.Inject

/**
 * @author Ritesh Shakya
 */

class GlideLoader @Inject constructor(val context: Context) : IImageLoader {

    override fun loadImage(
        url: String,
        holder: ImageView,
        onLoadSuccess: (Drawable) -> Unit,
        onLoadFail: (GlideException) -> Unit
    ) {
        if (url.isBlank()) return
        GlideApp.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    onLoadFail(e!!)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onLoadSuccess(resource)
                    return false
                }
            })
            .into(holder)
    }


    private fun clear(holder: ImageView) {
        holder.setImageDrawable(null)
    }
}
