package com.myjar.jarassignment.data.repository

import android.util.Log
import com.myjar.jarassignment.data.api.ApiService
import com.myjar.jarassignment.data.dao.database.LocalDao
import com.myjar.jarassignment.data.entity.LocalEntity
import com.myjar.jarassignment.data.model.ComputerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface JarRepository {
    suspend fun fetchResults(): Flow<List<ComputerItem>>
    suspend fun insertToLocal(item: List<LocalEntity>)
    fun fetchFromLocal(): Flow<List<LocalEntity>>
}

class JarRepositoryImpl(
    private val apiService: ApiService, private val localDao: LocalDao
) : JarRepository {
    override suspend fun fetchResults(): Flow<List<ComputerItem>> = flow {
        try {
            val response = apiService.fetchResults()
            emit(response)
        } catch (e: Exception) {
            Log.d("JARAPP", "fetchResults: $e")
        }
        }
    override suspend fun insertToLocal(item: List<LocalEntity>) {
        try {
            localDao.insertItem(item)
            Log.d("JARAPP", "insertToLocal: Successfully inserted items")
        } catch (e: Exception) {
            Log.e("JARAPP", "insertToLocal: Error - $e")
        }
    }

    override fun fetchFromLocal(): Flow<List<LocalEntity>> = flow {
        try {
            val localData = localDao.getListItem()
            emit(localData)
        } catch (e: Exception) {
            Log.e("JARAPP", "fetchFromLocal: Error - $e")
        }
    }
}
