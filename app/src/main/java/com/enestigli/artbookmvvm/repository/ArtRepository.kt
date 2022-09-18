package com.enestigli.artbookmvvm.repository

import androidx.lifecycle.LiveData
import com.enestigli.artbookmvvm.api.RetrofitAPI
import com.enestigli.artbookmvvm.model.Art
import com.enestigli.artbookmvvm.model.ImageResponse
import com.enestigli.artbookmvvm.room.ArtDao
import com.enestigli.artbookmvvm.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
        private val artDao:ArtDao,
        private val retrofitAPI: RetrofitAPI) : IArtRepository{


    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)              //return@let nedir anlamadım
                } ?: Resource.error("data error",null)
            }
            else{
                Resource.error("data error",null)
            }
        }
        catch (exception:Exception){
            return Resource.error("Data error",null)
        }
    }


}