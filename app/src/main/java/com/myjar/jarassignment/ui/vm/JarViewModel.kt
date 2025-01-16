package com.myjar.jarassignment.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjar.jarassignment.createRetrofit
import com.myjar.jarassignment.data.model.ComputerItem
import com.myjar.jarassignment.data.repository.JarRepository
import com.myjar.jarassignment.data.repository.JarRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JarViewModel : ViewModel() {

    private val _listStringData = MutableStateFlow<List<ComputerItem>>(emptyList())
    val listStringData: StateFlow<List<ComputerItem>>
        get() = _listStringData

    private val repository: JarRepository = JarRepositoryImpl(createRetrofit())


    private val _searched = MutableStateFlow<List<ComputerItem>>(emptyList())
    val search: StateFlow<List<ComputerItem>>
        get() = _searched

    fun fetchData() {
        viewModelScope.launch {
            val response = repository.fetchResults()
            response.collect {
                _listStringData.value = it
            }
        }
    }

    fun searchItem(query: String): StateFlow<List<ComputerItem>> {
        viewModelScope.launch {
            _listStringData.emit(_listStringData.value.filter {
                it.name.contains(query, ignoreCase = true)
            }
            )
        }
        return listStringData
    }
}