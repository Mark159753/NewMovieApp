package com.example.movieapp.data.local.typeConverters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

class ListIntConverter {

    @TypeConverter
    fun fromList(list:List<Int>?):String?{
        val moshi = Moshi.Builder().build()
        list?.let {
            return moshi.adapter<List<Int>>(List::class.java).toJson(it)
        }
        return null
    }

    @TypeConverter
    fun toList(list:String?):List<Int>?{
        val moshi = Moshi.Builder().build()
        list?.let {
            return moshi.adapter<List<Int>>(List::class.java).fromJson(it)
        }
        return null
    }
}