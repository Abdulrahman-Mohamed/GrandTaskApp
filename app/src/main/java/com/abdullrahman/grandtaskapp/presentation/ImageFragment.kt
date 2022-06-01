package com.abdullrahman.grandtaskapp.presentation

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abdullrahman.grandtaskapp.R
import com.abdullrahman.grandtaskapp.databinding.FragmentImageBinding
import com.abdullrahman.grandtaskapp.presentation.profileFragment.ProfileViewModel
import java.io.File
import java.io.FileOutputStream


class ImageFragment : Fragment() {
   lateinit var binding:FragmentImageBinding
   lateinit var viewModel:ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_image, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.share.setOnClickListener {
            val bitmap = binding.touchy.drawable.toBitmap()
            shareImageandText(bitmap)
        }
    }

    private fun shareImageandText(bitmap: Bitmap) {
        val uri: Uri? = getmageToShare(bitmap)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, "2 Grand")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Grand sharing image")
        intent.type = "image/png"
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    // Retrieving the url to share
    private fun getmageToShare(bitmap: Bitmap): Uri? {
        val imagefolder = File(requireActivity().cacheDir, "images")
        var uri: Uri? = null
        try {
            imagefolder.mkdirs()
            val file = File(imagefolder, "2_grand.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(requireContext(), "com.abdullrahman.grandtaskapp.fileprovider", file)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "" + e.message, Toast.LENGTH_LONG).show()
        }
        return uri
    }
}