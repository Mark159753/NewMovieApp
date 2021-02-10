package com.example.movieapp.data.local.typeConverters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

class ListStringConverter {

    @TypeConverter
    fun fromList(list:List<String>?):String?{
        val moshi = Moshi.Builder().build()
        list?.let {
            return moshi.adapter<List<String>>(List::class.java).toJson(it)
        }
        return null
    }

    @TypeConverter
    fun toList(list:String?):List<String>?{
        val moshi = Moshi.Builder().build()
        list?.let {
            return moshi.adapter<List<String>>(List::class.java).fromJson(it)
        }
        return null
    }
}