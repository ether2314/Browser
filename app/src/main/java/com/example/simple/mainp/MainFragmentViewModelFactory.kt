package com.example.simple.mainp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simple.database.BrowserDataBaseDao

class MainFragmentViewModelFactory (
    private val dataSource: BrowserDataBaseDao,
    private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)){
            return MainFragmentViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel class")
    }
}