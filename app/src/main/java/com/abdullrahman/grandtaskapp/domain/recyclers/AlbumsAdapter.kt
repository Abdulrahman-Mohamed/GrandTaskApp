package com.abdullrahman.grandtaskapp.domain.recyclers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.grandtaskapp.R
import com.abdullrahman.grandtaskapp.data.models.AlbumsItem
import com.abdullrahman.grandtaskapp.databinding.ItemsAlbumsBinding
import com.abdullrahman.grandtaskapp.domain.recyclers.onCLick.AlbumsOnClick

class AlbumsAdapter(val list: MutableList<AlbumsItem?>, context: Context?, val listener: AlbumsOnClick) : RecyclerView.Adapter<AlbumsAdapter.VHAlbums>() {
inner class VHAlbums(val dataItem:ItemsAlbumsBinding):RecyclerView.ViewHolder(dataItem.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHAlbums(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.items_albums,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VHAlbums, position: Int) {
        holder.dataItem.album = list[position]
        holder.dataItem.root.setOnClickListener {
            listener.onClick(list[position]!!)
        }
    }

    override fun getItemCount() = list.size
}
