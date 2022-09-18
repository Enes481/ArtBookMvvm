package com.enestigli.artbookmvvm.repository

import androidx.lifecycle.LiveData
import com.enestigli.artbookmvvm.model.Art
import com.enestigli.artbookmvvm.model.ImageResponse
import com.enestigli.artbookmvvm.util.Resource

interface IArtRepository {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art:Art)

    fun getArt():LiveData<List<Art>>

    suspend fun searchImage(imageString:String) : Resource<ImageResponse>

}