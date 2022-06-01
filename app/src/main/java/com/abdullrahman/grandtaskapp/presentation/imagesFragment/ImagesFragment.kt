package com.abdullrahman.grandtaskapp.presentation.imagesFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abdullrahman.grandtaskapp.R
import com.abdullrahman.grandtaskapp.data.models.PhotosItem
import com.abdullrahman.grandtaskapp.databinding.FragmentImagesBinding
import com.abdullrahman.grandtaskapp.domain.recyclers.onCLick.ImagesOnClick
import com.abdullrahman.grandtaskapp.presentation.profileFragment.ProfileViewModel

class ImagesFragment : Fragment(), ImagesOnClick {
    lateinit var binding: FragmentImagesBinding
    lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null)
                    viewModel.listOfPictures.observe(viewLifecycleOwner)
                    {
                        if (!it.isNullOrEmpty())
                            viewModel.search.value = it.filter { photosItem ->
                                photosItem!!.title!!.contains(
                                    p0
                                )
                            }

                    }
            }

            override fun afterTextChanged(p0: Editable?) {


            }

        })
    }

    override fun onClick(photos: PhotosItem) {
        viewModel.image.value = photos
        findNavController().navigate(R.id.action_imagesFragment_to_imageFragment)

    }
}