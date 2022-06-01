package com.abdullrahman.grandtaskapp

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.abdullrahman.grandtaskapp.data.dataSource.room.UserRoomEntity
import com.abdullrahman.grandtaskapp.databinding.ActivityMainBinding
import com.abdullrahman.grandtaskapp.presentation.profileFragment.ProfileViewModel
import com.abdullrahman.grandtaskapp.presentation.profileFragment.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Runnable
import java.net.InetAddress

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        retryConnect()
        binding.tvretry.visibility = View.GONE
        binding.animationView.visibility = View.GONE
       binding.tvretry.setOnClickListener {
           retryConnect()
       }


    }

    private fun retryConnect() {
        viewModel.getUseres()
        viewModel.getUsersLocal().observe(this) {
            if (!it.isNullOrEmpty())
            {
                if (!isNetworkConnected() && !internetIsConnected()) {
                    viewModel.localSave.value = it[0]

                    viewModel.userItem.value = null
                }
                binding.navHostFragment.visibility = View.VISIBLE
                getUserData()

            }else
            {
                if (!isNetworkConnected() && !internetIsConnected())
                {
                    binding.navHostFragment.visibility = View.GONE
                    binding.animationView.visibility = View.VISIBLE
                    binding.tvretry.visibility = View.VISIBLE






                }else
                {

                    binding.navHostFragment.visibility = View.VISIBLE
                    binding.animationView.visibility = View.GONE
                    binding.tvretry.visibility = View.GONE
                    getUserData()
                }
            }
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
    private fun getUserData() {
        viewModel.usersList.observe(this) { usersList ->
            if (!usersList.isNullOrEmpty() && viewModel.userItem.value == null) {
                val rnds = (0 until usersList!!.size).shuffled().last()
                println("random: $rnds")
                viewModel.userItem.value = usersList!![rnds]
                viewModel.getAlbums(viewModel.userItem.value!!.id!!)
                viewModel.listOfAlbums.observe(this) { albums ->
                    if (albums != null){
                        val user = UserRoomEntity(
                            id = usersList!![rnds].id,
                            address = usersList!![rnds].address!!.street + " " + usersList!![rnds].address!!.suite + " " + usersList!![rnds].address!!.city + " " + usersList!![rnds].address!!.zipcode,
                            name = usersList!![rnds].name!!, albums = albums!!
                        )
                        viewModel.getUsersLocal().observeOnce (this) {
                            if (!it.isNullOrEmpty()){
                                for(i in it)
                                viewModel.deleteUsers(i)
                                println(it.size)
                                viewModel.insertUser(user)
                            }
                            else {
                                viewModel.insertUser(user)
                            }
                        }
                    }
                }

            }
        }
    }

}