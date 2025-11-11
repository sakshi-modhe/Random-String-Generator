package com.alpha.randomstringgenerator.ui.viewmodel

import android.content.ContentResolver
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpha.randomstringgenerator.data.model.RandomString
import com.alpha.randomstringgenerator.data.repository.StringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(contentResolver: ContentResolver) : ViewModel() {

    private val repository = StringRepository(contentResolver)

    val stringList = mutableStateListOf<RandomString>()
    val errorMessage = mutableStateOf<String?>(null)
    val isLoading = mutableStateOf(false)

    fun generateRandomString(length: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            val result = repository.getRandomString(length)
            isLoading.value = false
            result.onSuccess {
                stringList.add(0, it)
                errorMessage.value = null
            }.onFailure {
                errorMessage.value = it.message
            }
        }
    }

    fun deleteAll() {
        stringList.clear()
    }

    fun deleteItem(item: RandomString) {
        stringList.remove(item)
    }
}