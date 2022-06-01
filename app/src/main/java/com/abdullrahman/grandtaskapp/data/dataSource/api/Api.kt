package com.abdullrahman.grandtaskapp.data.dataSource.api

import com.abdullrahman.grandtaskapp.data.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") userID: Long): Response<User>

    @GET("users")
    suspend fun getAllUser(): Response<MutableList<User>>

    @GET("users/{user_id}/albums")
    suspend fun getAlbums(@Path("user_id") userID: Long): Response<MutableList<AlbumsItem?>?>

    @GET("albums/{album_id}/photos")
    suspend fun getPhotos(@Path("album_id") albumID: Long): Response< List<PhotosItem?>?>
}