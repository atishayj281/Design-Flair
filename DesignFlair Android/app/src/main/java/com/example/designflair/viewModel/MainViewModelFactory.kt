package com.example.designflair.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.designflair.repository.ImageRepository
import com.example.designflair.repository.UserRepository

class MainViewModelFactory(private val imageRepository: ImageRepository,
                           private val userRepository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(imageRepository, userRepository) as T
    }
}