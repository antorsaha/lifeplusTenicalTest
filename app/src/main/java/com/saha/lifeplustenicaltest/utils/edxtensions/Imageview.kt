package com.saha.lifeplustenicaltest.utils.edxtensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.loadImage(
    imageUrl: String,
    placeHolder: Int? = null,
    onLoadComplete: (() -> Unit?)? = null
) {

    if (placeHolder != null) {
        Glide.with(context).load(imageUrl).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?, p1: Any?, target: Target<Drawable>, p3: Boolean
                ): Boolean {
                    //do something if error loading
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    p2: Target<Drawable>?,
                    dataSource: DataSource,
                    p4: Boolean
                ): Boolean {
                    onLoadComplete?.invoke()
                    return false
                }
            }).placeholder(placeHolder).into(this)
    } else {
        Glide.with(context).load(imageUrl).into(this)
    }


}