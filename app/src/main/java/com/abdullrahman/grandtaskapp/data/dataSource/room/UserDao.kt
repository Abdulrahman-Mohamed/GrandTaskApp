package com.abdullrahman.grandtaskapp.data.dataSource.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user:UserRoomEntity)

    @Query("SELECT * FROM UserRoomEntity")
    fun getUseres(): LiveData<List<UserRoomEntity>>
    @Query("DELETE  FROM UserRoomEntity")
    suspend fun deleteAll()
    @Delete()
    suspend fun delete(user: UserRoomEntity)
}