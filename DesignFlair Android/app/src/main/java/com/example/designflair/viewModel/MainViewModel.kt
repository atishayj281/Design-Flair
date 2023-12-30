package com.example.designflair.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designflair.model.*
import com.example.designflair.repository.ImageRepository
import com.example.designflair.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val imageRepository: ImageRepository,
                    private val userRepository: UserRepository): ViewModel() {

    val image: LiveData<Image>
        get() = imageRepository.image

    val scaledImage: LiveData<Image>
        get() = imageRepository.upScaledImage

    val authResult: LiveData<AuthResult>
        get() = userRepository.authResult

    val token: LiveData<String>
    get() = userRepository.token

    fun clearImage(){
        imageRepository.clearImage()
    }

    fun clearScaledImage(){
        imageRepository.clearScaledImage()
    }

    fun getImage(prompt: Prompt) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("token", token.value.toString())
            imageRepository.getImage(prompt, "Bearer " + token.value.toString())
        }
    }

    fun getUpScaledImage(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getUpScaledImage(image, "Bearer " + token.value.toString())
        }
    }

    fun register(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.register(user)
        }
    }

    fun generateToken(authRequest: AuthRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.generateToken(authRequest)
        }
    }

    fun setToken(token: String) {
        userRepository.setToken(token)
    }

    fun getToken() {
        userRepository.getToken()
    }

}