package com.myjar.jarassignment.ui.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.dao.LocalDao
import com.myjar.jarassignment.data.entity.LocalEntity
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JarViewModel(localDao: LocalDao) : ViewModel() {

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>>
        get() = _listStringData

    private val repository: JarRepository = JarRepositoryImpl(createRetrofit(), localDao)

    /**
     * Fetch data from remote API and save to local, or fallback to local data if offline.
     */
    fun fetchData() {
        viewModelScope.launch {
            try {
                repository.fetchResults().collect { remoteData ->
                    println("Remote data fetched: $remoteData")
                    _listStringData.value = remoteData
                    repository.insertToLocal(remoteData.map { computerItem ->
                        LocalEntity(
                            name = computerItem.name,
                            id = computerItem.id
                        )
                    })
                }
            } catch (e: Exception) {
                println("Error fetching from API: $e")
                fetchFromLocal()
            }
        }
    }

    /**
     * Fetch data from the local database.
     */
    private fun fetchFromLocal() {
        viewModelScope.launch {
            try {
                repository.fetchFromLocal().collect { localData ->
                    Log.d("LocalData", localData.toString())
                    _listStringData.value = localData.map { localEntity ->
                        ComputerItem(
                            id = localEntity.id,
                            name = localEntity.name,
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("LocalData", "Error fetching local data: $e")
                println("Error fetching local data: $e")
            }
        }
    }

    /**
     * Search for items in the current data set.
     */
    fun searchItem(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                fetchData()
            } else {
                val filteredList = _listStringData.value.filter {
                    it.name.contains(query, ignoreCase = true)
                }
                _listStringData.value = filteredList
            }
        }
    }
}