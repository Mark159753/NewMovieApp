package com.example.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entitys.Trends

@Dao
interface TrendsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item:Trends)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items:List<Trends>)

    @Query("SELECT * FROM trend_tab ORDER BY primaryKey ASC")
    fun getPagingSource():PagingSource<Int, Trends>

    @Query("SELECT * FROM trend_tab")
    suspend fun getAllItems():List<Trends>

    @Query("DELETE FROM trend_tab")
    suspend fun deleteAllItems()
}