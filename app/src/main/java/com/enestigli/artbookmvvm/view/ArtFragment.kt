package com.enestigli.artbookmvvm.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enestigli.artbookmvvm.R
import com.enestigli.artbookmvvm.adapter.ArtRecyclerAdapter
import com.enestigli.artbookmvvm.databinding.FragmentArtsBinding
import com.enestigli.artbookmvvm.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ArtFragment @Inject constructor(
    val artRecyclerAdapter:ArtRecyclerAdapter)

    : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding :FragmentArtsBinding?=null
    lateinit var viewModel:ArtViewModel //Bu şekilde de viewmodeli alabiliriz inject e edebiliriz.


    private val swipeCallBack = object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT )  //kaydırma işlemi için
    {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
           val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.artList[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.recyclerviewArt.adapter = artRecyclerAdapter
        binding.recyclerviewArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerviewArt)

        binding.fab.setOnClickListener{
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.artList = it
        })
    }




    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()

    }
}