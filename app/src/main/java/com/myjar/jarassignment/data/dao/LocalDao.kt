package com.myjar.jarassignment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myjar.jarassignment.data.entity.LocalEntity

@Dao
interface LocalDao {

    @Query("SELECT * from LocalEntity")
   suspend fun getListItem() : List<LocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: List<LocalEntity>)
}