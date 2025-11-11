package com.alpha.randomstringgenerator.ui.viewmodel

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val contentResolver: ContentResolver
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(contentResolver) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}