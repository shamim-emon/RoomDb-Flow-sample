package com.example.roomdb_flow_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class XViewModelFactory(private val repo: XRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(XViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return XViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}