package com.abdullrahman.grandtaskapp.presentation.profileFragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullrahman.grandtaskapp.R
import com.abdullrahman.grandtaskapp.data.dataSource.room.UserRoomEntity
import com.abdullrahman.grandtaskapp.data.models.AlbumsItem
import com.abdullrahman.grandtaskapp.databinding.FragmentProfileBinding
import com.abdullrahman.grandtaskapp.domain.recyclers.AlbumsAdapter
import com.abdullrahman.grandtaskapp.domain.recyclers.onCLick.AlbumsOnClick

class ProfileFragment : Fragment(),AlbumsOnClick{
    lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.visibility = View.GONE
        viewModel.userItem.observeOnce(viewLifecycleOwner){
            if (it != null)
            {
                //viewModel.getUseres()
            }
            else{
                getLocalUser()
            }
            binding.root.visibility = View.VISIBLE

        }

            }



    private fun getLocalUser() {

            viewModel.localSave.observeOnce  (requireActivity()) {
                if (it != null) {

                    val layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    binding.rvAlbums.layoutManager = layoutManager
                    binding.rvAlbums.setHasFixedSize(true)
                    binding.rvAlbums.addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            layoutManager.orientation
                        )
                    )
                    binding.rvAlbums.adapter = AlbumsAdapter(
                        it.albums!!,
                        requireContext(),
                        this
                    )
                    binding.tvName.text = it.name
                    binding.tvAddress.text = it.address

                }
            }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

    override fun onClick(album: AlbumsItem) {
            viewModel.getPictures(album.id!!)


                if (isNetworkConnected() && internetIsConnected())
            findNavController().navigate(R.id.action_profileFragment_to_imagesFragment)




    }

}



fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object: Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, object: Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}
