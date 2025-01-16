package com.myjar.jarassignment.data.repository

import android.util.Log
import com.myjar.jarassignment.data.api.ApiService
import com.myjar.jarassignment.data.dao.LocalDao
import com.myjar.jarassignment.data.entity.LocalEntity
import com.myjar.jarassignment.data.model.ComputerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface JarRepository {
    suspend fun fetchResults(): Flow<List<ComputerItem>>
    suspend fun insertToLocal(item: List<LocalEntity>)
    fun fetchFromLocal(): Flow<List<ComputerItem>>
}

class JarRepositoryImpl(
    private val apiService: ApiService, private val localDao: LocalDao
) : JarRepository {
    override suspend fun fetchResults(): Flow<List<ComputerItem>> = flow {
        try {
            // Try fetching from API
            val response = apiService.fetchResults()
            emit(response)
        } catch (e: Exception) {
            Log.e("JARAPP", "fetchResults: Failed to fetch from API. Fetching from local.", e)
            val localData = localDao.getListItem().map {
                ComputerItem(id = it.id.toString(), name = it.name)
            }
            emit(localData)
        }
    }

    override suspend fun insertToLocal(item: List<LocalEntity>) {
        try {
            localDao.insertItem(item)
            Log.d("JARAPP", "Items successfully inserted into local database.")
        } catch (e: Exception) {
            Log.e("JARAPP", "Error inserting items to local database - $e")
        }
    }

    override fun fetchFromLocal(): Flow<List<ComputerItem>> = flow {
        emit(fetchLocalItems())
    }

    private suspend fun fetchLocalItems(): List<ComputerItem> {
        return try {
            localDao.getListItem().map {
                ComputerItem(id = it.id.toString(), name = it.name)
            }
        } catch (e: Exception) {
            Log.e("JARAPP", "Error fetching from local database - $e")
            emptyList()
        }
    }
}
