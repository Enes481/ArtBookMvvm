package com.enestigli.artbookmvvm.di

import android.content.Context
import androidx.room.Room
import com.enestigli.artbookmvvm.room.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("testDatabase") //burda bizim normal database imiz ile test databaseimizin injectionları karışmasın diye bu şekilde isim verdik ,Bu şekilde isim verilebiliyor
    fun injectInMemoryRoom(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context,ArtDatabase::class.java)
            .allowMainThreadQueries()
            .build()



}