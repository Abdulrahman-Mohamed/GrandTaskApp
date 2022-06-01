package com.abdullrahman.grandtaskapp.domain

import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.grandtaskapp.data.models.AlbumsItem
import com.abdullrahman.grandtaskapp.data.models.PhotosItem
import com.abdullrahman.grandtaskapp.data.models.User
import com.abdullrahman.grandtaskapp.domain.recyclers.AlbumsAdapter
import com.abdullrahman.grandtaskapp.domain.recyclers.ImagesAdapter
import com.abdullrahman.grandtaskapp.domain.recyclers.onCLick.AlbumsOnClick
import com.abdullrahman.grandtaskapp.domain.recyclers.onCLick.ImagesOnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.ortiz.touchview.TouchImageView

@BindingAdapter("cnc_text")
fun ConcatStrings(text: TextView, user: User?) {
    if (user != null) {
        var addres = ""
        addres =
            user.address!!.street + " " + user.address!!.suite + " " + user.address!!.city + " " + user.address!!.zipcode
        text.text = addres
    }
}

@BindingAdapter("albums_adapter", "albums_listener")
fun setAdapterAlbums(view: RecyclerView, list: MutableList<AlbumsItem?>?, listener: AlbumsOnClick) {
    if (list != null) {
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        view.layoutManager = layoutManager
        view.setHasFixedSize(true)
        view.addItemDecoration(
            DividerItemDecoration(
                view.context,
                layoutManager.orientation
            )
        )
        view.adapter = AlbumsAdapter(list!!, view.context, listener)
    }
}

@BindingAdapter("images_adapter", "images_listener")
fun setAdapterImages(view: RecyclerView, list: MutableList<PhotosItem?>?, listener: ImagesOnClick) {
    if (list != null) {
        val layoutManager = GridLayoutManager(view.context, 3)
        view.layoutManager = layoutManager
        view.setHasFixedSize(true)
        view.adapter = ImagesAdapter(list!!, view.context, listener)
    }
}

@BindingAdapter("photos_src")
fun setImage(imageView: ImageView, url: String) {
    val url =
        GlideUrl(url, LazyHeaders.Builder().addHeader("User-Agent", "your-user-agent").build())

    Glide.with(imageView.context)
        .load(url)
        .fitCenter()
        .into(imageView)
}
@BindingAdapter("text_res")
fun setImage(tv: TextView, resource: String?) {
    if (resource !=  null)
        tv.text = resource
}
@BindingAdapter("touch_src")
fun settouch(imageView: TouchImageView, url: String) {
    val url =
        GlideUrl(url, LazyHeaders.Builder().addHeader("User-Agent", "your-user-agent").build())

    Glide.with(imageView.context)
        .load(url)
        .fitCenter()
        .into(imageView)
}

