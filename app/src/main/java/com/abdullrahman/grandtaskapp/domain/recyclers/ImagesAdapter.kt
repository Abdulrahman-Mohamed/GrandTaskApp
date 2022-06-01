package com.abdullrahman.grandtaskapp.domain.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.grandtaskapp.R
import com.abdullrahman.grandtaskapp.data.models.PhotosItem
import com.abdullrahman.grandtaskapp.databinding.ItemImagesBinding
import com.abdullrahman.grandtaskapp.databinding.ItemsAlbumsBinding
import com.abdullrahman.grandtaskapp.domain.recyclers.onCLick.ImagesOnClick

class ImagesAdapter(val list:  List<PhotosItem?>?,context:Context,val listener:ImagesOnClick) : RecyclerView.Adapter<ImagesAdapter.VHImages>() {
    inner class VHImages(val dataItem: ItemImagesBinding) : RecyclerView.ViewHolder(dataItem.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHImages(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_images,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VHImages, position: Int) {
        holder.dataItem.itemImage = list!![position]
        holder.dataItem.root.setOnClickListener {
            listener.onClick(list!![position]!!)
        }
    }

    override fun getItemCount() = list!!.size

}