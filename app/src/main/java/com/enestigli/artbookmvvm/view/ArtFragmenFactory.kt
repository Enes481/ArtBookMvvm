package com.enestigli.artbookmvvm.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.enestigli.artbookmvvm.adapter.ArtRecyclerAdapter
import com.enestigli.artbookmvvm.adapter.ImageRecyclerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide:RequestManager,
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
):FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApıFragment::class.java.name -> ImageApıFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)

        }




    }


}