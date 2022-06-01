package com.abdullrahman.grandtaskapp.data.dataSource.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserRoomEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase:RoomDatabase() {
    companion object {
        const val DatabaseName = "AppDataBase"
    }
    abstract fun UserDao(): UserDao
}