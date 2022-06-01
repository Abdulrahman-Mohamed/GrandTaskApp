package com.abdullrahman.grandtaskapp.presentation.profileFragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.abdullrahman.grandtaskapp.data.dataSource.room.UserRoomEntity
import com.abdullrahman.grandtaskapp.data.models.*
import com.abdullrahman.grandtaskapp.domain.utils.Response
import com.abdullrahman.grandtaskapp.repositories.mainRepo.MainRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val mainRepoImp: MainRepoImp, private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val nameContext = "MainViewModel"
    val usersList = MutableLiveData<MutableList<User>?>(null)
    val userItem = MutableLiveData<User?>()
    val listOfAlbums = MutableLiveData<MutableList<AlbumsItem?>?>()
    val localSave = MutableLiveData<UserRoomEntity?>(null)
    val listOfPictures = MutableLiveData< List<PhotosItem?>?>()
    var search = MutableLiveData< List<PhotosItem?>?>()
    val image = MutableLiveData<PhotosItem>()

    init {



    }
    fun getPictures(albumId:Long){
        val job = viewModelScope.launch(Dispatchers.IO) {
            when (val result = mainRepoImp.getPhotos(albumId)) {
                is Response.Success -> {
                    listOfPictures.postValue(result.data!!)
                    search.postValue(result.data!!)
                }
                is Response.Error -> {
                    Log.e(nameContext, result.message!!)
                }
            }
        }

    }
    fun getUsersLocal():LiveData<List<UserRoomEntity>> {

            return mainRepoImp.getLocalUseres()
    }
    fun deleteUsers(user:UserRoomEntity) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            mainRepoImp.deleteUserDao(user)
        }
        runBlocking {
            job.join()
        }
    }
    fun deleteAllUsers() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            mainRepoImp.deleteDao()
        }

    }

    fun insertUser(user: UserRoomEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoImp.insertUser(user)
        }
    }

    fun getAlbums(userId: Long) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            when (val result = mainRepoImp.getAlbums(userId)) {
                is Response.Success -> {
                    listOfAlbums.postValue(result.data!!)

                }
                is Response.Error -> {
                    Log.e(nameContext, result.message!!)
                }
            }
        }
        runBlocking { job.join() }
    }

    fun getUseres() {
        val job = viewModelScope.launch(Dispatchers.IO) {
            when (val result = mainRepoImp.getAllUsers()) {
                is Response.Success -> {
                    usersList.postValue(result.data!!)

                }
                is Response.Error -> {
                    Log.e(nameContext, result.message!!)
                }
            }
        }

    }

}