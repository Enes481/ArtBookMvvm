package com.enestigli.artbookmvvm.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.enestigli.artbookmvvm.getOrAwaitValue
import com.enestigli.artbookmvvm.model.Art
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@SmallTest //small teslter unit test anlamına geliyor , testler 3'e ayrılır small testler,medium testler,large testler, koymasakda olur
@HiltAndroidTest // hilti kullanarak test yapıcağımız belirtmek için
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() // bu bütün her şeyi main threadde çalıştıracağımız söylüyor.



    @get:Rule
    var hiltRule = HiltAndroidRule(this) // dökümantasyonda bu şekilde get vermek gerekiyor hilt için

    @Inject
    @Named("testDatabase") // normal database ile testdatabase karışmasın diye isim verdik ,bu isimlendirmeyi TestAppModule'de de kullandık injection için onu bulup buraya getiriyor bu şekilde karışmıyor.
    lateinit var database: ArtDatabase //private değişkenlere ınjection yapılamıyor

    private lateinit var dao : ArtDao
    //private lateinit var database :ArtDatabase



    @Before
    fun setup(){
       /* database = Room.inMemoryDatabaseBuilder( //inMemoryDatabaseBuilder -> bilgileri ram'de saklıyor ve proccess killed olduğu zaman siliyor. Bu daha çok testler için kullanılıyor
            ApplicationProvider.getApplicationContext(),     //ApplicationProvider -> Testler içerisinde contexti bu şekilde alabiliyoruz.
            ArtDatabase::class.java,
        ).allowMainThreadQueries().build()         //allowMainThreadQueries -> main thread de çalışıcağımızı söylüyoruz.

        dao = database.artDao()*/

        hiltRule.inject() // hiltRule kullanırken  başlatmak için bu şekilde hiltRule.inject dememiz gerekiyor başlamadan önce
        dao = database.artDao()
    }


    @After
    fun tearDown(){
        database.close() // her şet bittikten sonra baştan bir database oluşturmak için yaptık,var olanı sildik
    }

    @Test
    fun insertArtTesting() = runBlocking{
        //runBlockingTest -> Biz testlerde coroutine istemediğimiz için bu bizim için her threadi sıra sıra çalıştırıyor. yani threading i bir nevi ortadan kaldırıyoruz. çünkü biz artdao da suspend fonks kullandık suspend coroutineler ile çalışıyor.

        val exampleArt = Art("Mona lisa","davinci",1800,"test.com",1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)




    }

    @Test
    fun deleteArtTesting() = runBlocking{

        val exampleArt = Art("scare","davinci",1500,"test.com",1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)

    }

}