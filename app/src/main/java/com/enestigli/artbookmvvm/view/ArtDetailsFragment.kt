package com.enestigli.artbookmvvm.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.enestigli.artbookmvvm.R
import com.enestigli.artbookmvvm.databinding.FragmentArtDetailsBinding
import com.enestigli.artbookmvvm.util.Status
import com.enestigli.artbookmvvm.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(
     val glide:RequestManager
): Fragment(R.layout.fragment_art_details) {

    private var fragmentartdetailsbinding : FragmentArtDetailsBinding? = null
    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        subscribeToObservers()
        val binding  = FragmentArtDetailsBinding.bind(view)
        fragmentartdetailsbinding = binding



        binding.artImageView.setOnClickListener{
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApıFragment())

        }

        val callback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener{
            viewModel.makeArt(
                binding.nameText.text.toString(),
                binding.artistText.text.toString(),
                binding.yearText.text.toString())
        }
    }

    private fun subscribeToObservers(){

        viewModel.selectedImageURL.observe(viewLifecycleOwner, androidx.lifecycle.Observer{url->

            fragmentartdetailsbinding?.let {binding->
                glide.load(url).into(binding.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

                when(it.status){
                    Status.SUCCESS ->{
                        Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                        findNavController().popBackStack()
                        viewModel.resetInsertArtMsg()//art message success den errora loading e çevirmemek için yaptık ,boş bıraktık

                    }

                    Status.ERROR ->{
                        Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                    }

                    Status.LOADING ->{

                    }
                }
        })

        }



    override fun onDestroyView() {
        fragmentartdetailsbinding = null
        super.onDestroyView()
    }
}
