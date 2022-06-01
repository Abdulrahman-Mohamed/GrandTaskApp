package com.abdullrahman.grandtaskapp.data.dataSource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdullrahman.grandtaskapp.data.models.Address
import com.abdullrahman.grandtaskapp.data.models.AlbumsItem

@Entity
@JvmSuppressWildcards
@TypeConverters(
    Converter::class
)
data class UserRoomEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "albums")
    val albums: MutableList<AlbumsItem?>
)