package com.example.designflair.api

import com.example.designflair.model.Image
import com.example.designflair.model.Prompt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ImageService {
    @POST("/design/v1/get")
    suspend fun getImage(@Body prompt: Prompt, @Header("Authorization") authHeader: String): Response<Image>

    @POST("/design/v1/upscale")
    suspend fun getUpScaledImage(@Body image: Image, @Header("Authorization") authHeader: String): Response<Image>
}
