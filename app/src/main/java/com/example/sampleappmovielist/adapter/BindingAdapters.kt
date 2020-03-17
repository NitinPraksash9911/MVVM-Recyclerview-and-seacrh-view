package com.example.sampleappmovielist.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


/**
 * Created by Nitin  on 2020-03-17.
 */
class BindingAdapters {

    companion object {
        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String) {
            Glide.with(view.context)
                .apply {
                    RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                }
                .load(imageUrl)
                .into(view)
        }


    }
}