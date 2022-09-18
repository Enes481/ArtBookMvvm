package com.enestigli.artbookmvvm.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enestigli.artbookmvvm.model.Art

@Database(entities = [Art::class], version = 1)
abstract class ArtDatabase :RoomDatabase(){

    abstract fun artDao() : ArtDao
}