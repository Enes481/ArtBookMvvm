package com.enestigli.artbookmvvm.view

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.enestigli.artbookmvvm.R
import com.enestigli.artbookmvvm.adapter.ImageRecyclerAdapter
import com.enestigli.artbookmvvm.databinding.FragmentImageApiBinding
import com.enestigli.artbookmvvm.util.Status
import com.enestigli.artbookmvvm.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApıFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
    ) :Fragment(R.layout.fragment_image_api) {

    private var fragmentBinding : FragmentImageApiBinding? = null
    lateinit var viewModel:ArtViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        var job:Job? = null

        binding.searchText.addTextChangedListener{
            job?.cancel() //hali hazırda bir job varsa onu iptal ettik
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }           //Burda search de kullanıcı kelime araken ki işlemleri  takip ediyoruz, Bunun için coroutine leri kullanıyoruz

        }
        subscribeToObservers()

        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        imageRecyclerAdapter.setOnItemClickListener {
            //string veriyor bu string tıklanan resmin url si
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)

        }


    }

    private fun subscribeToObservers(){

        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {

                    val urls = it.data?.hits?.map { imageResult -> imageResult.previewURL }

                    imageRecyclerAdapter.imageList = urls ?: listOf()
                    fragmentBinding?.imageApiProgressBar?.visibility = View.GONE
                }

                Status.LOADING ->{
                    Toast.makeText(requireContext(),it.message?:"Error",Toast.LENGTH_LONG).show()
                    fragmentBinding?.imageApiProgressBar?.visibility = View.GONE
                }

                Status.ERROR -> {
                    fragmentBinding?.imageApiProgressBar?.visibility = View.VISIBLE
                }


            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}