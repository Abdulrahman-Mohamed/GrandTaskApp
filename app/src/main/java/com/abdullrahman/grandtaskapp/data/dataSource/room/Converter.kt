package com.abdullrahman.grandtaskapp.data.dataSource.room

import androidx.room.TypeConverter
import com.abdullrahman.grandtaskapp.data.models.AlbumsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromAlbumsItemList(value: MutableList<AlbumsItem>): String {
            val gson = Gson()
            val type = object : TypeToken<MutableList<AlbumsItem>>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun toAlbumsItemList(value: String): MutableList<AlbumsItem> {
            val gson = Gson()
            val type = object : TypeToken<MutableList<AlbumsItem>>() {}.type
            return gson.fromJson(value, type)
        }
    }
}