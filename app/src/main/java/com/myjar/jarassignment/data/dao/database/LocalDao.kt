package com.myjar.jarassignment.data.dao.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myjar.jarassignment.data.entity.LocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    @Query("SELECT * from LocalEntity")
    fun getListItem() : List<LocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: List<LocalEntity>)
}