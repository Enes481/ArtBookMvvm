package com.enestigli.artbookmvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enestigli.artbookmvvm.MainCoroutineRule
import com.enestigli.artbookmvvm.getOrAwaitValueTest
import com.enestigli.artbookmvvm.repository.FakeArtRepository
import com.enestigli.artbookmvvm.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    private lateinit var viewModel :ArtViewModel

    @get:Rule //@get:Rule yaparak burda kullanacağımız rule ları belirtiriyoruz.
    var instantTaskExecutorRule = InstantTaskExecutorRule() //Biz livedata ,coroutine ,threading istemiyoruz, düz bir şekilde çalıştırmak istiyoruz. Bu herşeyi main threadde çalıştırmamızı sağlıyor. Biz şuan ayrıca android test içerisinde değilizi, test içerisindeyiz o yüzden burda main thread diye bir şey yok ,ui thread yok

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()  //Bu iki @get:Rule tek bir main thread iş yapmamızı sağlıyor.

    @Before
    fun setup(){
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){
          viewModel.makeArt("mona lisa","da vinci","")
          val value = viewModel.insertArtMessage.getOrAwaitValueTest()       // value livedata testlerde livedata istemiyoruz çünkü livedata asenkron çalışıyor,testlerde herşey senkron çalışmalı
          assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("","da vinci","1800")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()       // value livedata testlerde livedata istemiyoruz çünkü livedata asenkron çalışıyor,testlerde herşey senkron çalışmalı
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("mona lisa","","1800")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()       // value livedata testlerde livedata istemiyoruz çünkü livedata asenkron çalışıyor,testlerde herşey senkron çalışmalı
        assertThat(value.status).isEqualTo(Status.ERROR)
    }


}