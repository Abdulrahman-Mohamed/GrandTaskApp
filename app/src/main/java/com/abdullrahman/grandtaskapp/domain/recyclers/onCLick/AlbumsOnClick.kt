package com.abdullrahman.grandtaskapp.domain.recyclers.onCLick

import com.abdullrahman.grandtaskapp.data.models.AlbumsItem

interface AlbumsOnClick {
    fun onClick(album: AlbumsItem)
}