package com.alpha.randomstringgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alpha.randomstringgenerator.ui.screens.MainScreen
import com.alpha.randomstringgenerator.ui.viewmodel.MainViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import com.alpha.randomstringgenerator.ui.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(contentResolver)
            )
            MainScreen(viewModel)
        }
    }
}
