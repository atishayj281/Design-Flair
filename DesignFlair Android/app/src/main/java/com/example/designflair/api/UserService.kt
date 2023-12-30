package com.example.designflair.api

import com.example.designflair.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/user")
    suspend fun register(@Body user: User): Response<AuthResult>

    @POST("/user/generateToken")
    suspend fun generateToken(@Body authRequest: AuthRequest): Response<AuthResult>

}