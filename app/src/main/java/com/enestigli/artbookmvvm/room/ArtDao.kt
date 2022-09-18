package com.enestigli.artbookmvvm.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.enestigli.artbookmvvm.model.Art

@Dao
interface ArtDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //id'ler çakışırsa ne yapayım
    suspend fun insertArt(art:Art) //suspend fonks. corutine ler ile kullanılıyor. asenkron olarak çalıştırdığımız gerektirdiğinde durdurulabilen ve devam edebilen fonkslardır. Veri tabanı işlermleri yapıyoruz ve bunları main thread de çalıştırmak istemiyoruz.

    @Delete
    suspend fun deleteArt(art:Art)

    @Query("SELECT * FROM arts")
    fun observeArts():LiveData<List<Art>> //suspend yazmadık çünkü livedata zaten asenkron çalışır.



}