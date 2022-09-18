package com.enestigli.artbookmvvm.viewmodel

import androidx.core.widget.ListViewAutoScrollHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enestigli.artbookmvvm.model.Art
import com.enestigli.artbookmvvm.model.ImageResponse
import com.enestigli.artbookmvvm.repository.IArtRepository
import com.enestigli.artbookmvvm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel  @Inject constructor(
    private val repository: IArtRepository   //Normalde her activity ya da fragment için bir viewmodel olmalı ancak biz burda bir tane yaptık
): ViewModel() {

    // art fragment

    val artList = repository.getArt()

    //image api fragment

    private val images = MutableLiveData<Resource<ImageResponse>>() //private yaptık hem de mutable yaptık yani değiştirilebilir livadata yaptık ancak private olduğu için sadece bu sınıf içerisinden değiştirilebilir.
    val imageList : LiveData<Resource<ImageResponse>>
        get() = images   //imageList images ı alıyor get ile bunu yapmamızın sebebi dışarıdan buna get ile erişmek ama LiveData dediğimiz içinde set edemiyoruz. dışarıdan get ile ulaşılabilsin set edilemesin dedik. Sektörde de zaten böyle kullanılıyor daha çok.  Bunu yapmayabilirdikde


    private val selectedImage = MutableLiveData<String>()
    val selectedImageURL :LiveData<String>
        get() = selectedImage

    // Art Details Fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage:LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }


    fun setSelectedImage(url:String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art:Art) = viewModelScope.launch{
        repository.deleteArt(art)
    }


    fun insertArt(art:Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun searchForImage(searchString:String){

        if(searchString.isEmpty()){
            return
        }

        images.value = Resource.loading(null)

        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }

    }


    fun makeArt(name:String,artistName:String,year:String){

        if(name.isEmpty() || artistName.isEmpty() || year.isEmpty() ){
            insertArtMsg.postValue(Resource.error("Enter Name,artist,year",null))
            return
        }


        val yearInt = try{

                year.toInt()

        }catch (e:Exception){
            insertArtMsg.postValue(Resource.error("year should be number.",null))
            return
        }

        val art = Art(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }







}