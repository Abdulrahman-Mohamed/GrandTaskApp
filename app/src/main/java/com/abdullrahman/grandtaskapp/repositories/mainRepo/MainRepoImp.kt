package com.abdullrahman.grandtaskapp.repositories.mainRepo

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.abdullrahman.grandtaskapp.data.dataSource.api.Api
import com.abdullrahman.grandtaskapp.data.dataSource.room.UserDao
import com.abdullrahman.grandtaskapp.data.dataSource.room.UserRoomEntity
import com.abdullrahman.grandtaskapp.data.models.*
import com.abdullrahman.grandtaskapp.domain.utils.Response
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.annotation.meta.When
import javax.inject.Inject

class MainRepoImp @Inject constructor(
    @ApplicationContext val context: Context,
    val api: Api,
    val userDao: UserDao
) : MainRepo {
    override suspend fun getUser(userId: Long): Response<User> {
        if (isNetworkConnected() && internetIsConnected()) {
            val result = api.getUser(userId)
            return if (result.isSuccessful)

                Response.Success(result.body()!!)
            else
                Response.Error(result.errorBody()!!.toString())
        } else
        {
            withContext(Dispatchers.Main){
                Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }    }

    override suspend fun getAllUsers(): Response<MutableList<User>> {
        if (isNetworkConnected() && internetIsConnected()) {
            val result = api.getAllUser()
            return if (result.isSuccessful)

                Response.Success(result.body()!!)
            else
                Response.Error(result.errorBody()!!.toString())
        } else
        {
            withContext(Dispatchers.Main){
                Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override suspend fun getAlbums(userId: Long): Response<MutableList<AlbumsItem?>?> {
        if (isNetworkConnected() && internetIsConnected()) {
            val result = api.getAlbums(userId)
            return if (result.isSuccessful)

                Response.Success(result.body()!!)
            else
                Response.Error(result.errorBody()!!.toString())
        } else
        {
            withContext(Dispatchers.Main){
                Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override suspend fun getPhotos(albumId: Long): Response< List<PhotosItem?>?> {
        if (isNetworkConnected() && internetIsConnected()) {
            val result = api.getPhotos(albumId)
            return if (result.isSuccessful)

                Response.Success(result.body()!!)
            else
                Response.Error(result.errorBody()!!.toString())
        } else {
            withContext(Dispatchers.Main){
                Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show()

            }
            return Response.Error("No Internet Connection")
        }
    }

    override suspend fun insertUser(user: UserRoomEntity) {
        userDao.insertUser(user)
    }

    suspend fun deleteUserDao(user: UserRoomEntity) {
        userDao.delete(user)
    }

    override suspend fun deleteDao() {
        userDao.deleteAll()
    }

    override fun getLocalUseres(): LiveData<List<UserRoomEntity>> {
        return userDao.getUseres()
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}