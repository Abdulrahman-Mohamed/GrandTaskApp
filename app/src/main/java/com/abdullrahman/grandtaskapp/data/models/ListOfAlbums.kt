package com.abdullrahman.grandtaskapp.data.models

import com.google.gson.annotations.SerializedName

data class ListOfAlbums(

	@field:SerializedName("ListOfAlbums")
	val listOfAlbums: MutableList<AlbumsItem?>? = null
)

data class AlbumsItem(

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)
