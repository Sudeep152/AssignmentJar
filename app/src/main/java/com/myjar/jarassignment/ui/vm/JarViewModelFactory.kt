package com.myjar.jarassignment.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myjar.jarassignment.data.dao.LocalDao

class JarViewModelFactory(
    private val localDao: LocalDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JarViewModel::class.java)) {
            return JarViewModel(localDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}