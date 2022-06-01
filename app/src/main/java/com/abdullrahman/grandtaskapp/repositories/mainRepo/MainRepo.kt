package com.abdullrahman.grandtaskapp.repositories.mainRepo

import androidx.lifecycle.LiveData
import com.abdullrahman.grandtaskapp.data.dataSource.room.UserRoomEntity
import com.abdullrahman.grandtaskapp.data.models.*
import com.abdullrahman.grandtaskapp.domain.utils.Response

interface MainRepo {
    suspend fun getUser(userId:Long):Response<User>
    suspend fun getAllUsers():Response<MutableList<User>>
    suspend fun getAlbums(userId:Long):Response<MutableList<AlbumsItem?>?>
    suspend fun getPhotos(albumId:Long):Response< List<PhotosItem?>?>
    suspend fun insertUser(user:UserRoomEntity)
    suspend fun deleteDao()
     fun getLocalUseres():LiveData<List<UserRoomEntity>>
}