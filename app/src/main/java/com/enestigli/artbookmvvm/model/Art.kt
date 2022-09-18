package com.enestigli.artbookmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(

    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "artistName") val artistName: String?,
    @ColumnInfo(name = "year") val year: Int?,
    @ColumnInfo(name = "imageUrl") val imgUrl: String?,
    @PrimaryKey(autoGenerate = true) val artId :Int? = null


    )