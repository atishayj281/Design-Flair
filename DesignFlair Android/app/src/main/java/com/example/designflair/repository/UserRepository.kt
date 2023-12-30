package com.example.designflair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.designflair.api.UserService
import com.example.designflair.model.AuthRequest
import com.example.designflair.model.AuthResult
import com.example.designflair.model.User
import com.example.designflair.preferences.UserPreferences

class UserRepository(private val userService: UserService, private val userPreferences: UserPreferences) {
    private val authResultLiveData = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult>
    get() = authResultLiveData

    private val tokenLiveData = MutableLiveData<String>()
    val token: LiveData<String>
    get() = tokenLiveData

    suspend fun register(user: User) {
        val result = userService.register(user)
        if(result.body() != null) {
            authResultLiveData.postValue(result.body())
        }
    }

    suspend fun generateToken(authRequest: AuthRequest) {
        val result = userService.generateToken(authRequest)
        if(result.body() != null) {
            authResultLiveData.postValue(result.body())
        }
    }

    fun getToken() {
        tokenLiveData.postValue(userPreferences.getToken())
    }

    fun setToken(token: String) {
        userPreferences.setToken(token)
    }
}