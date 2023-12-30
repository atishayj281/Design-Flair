package com.example.designflair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.designflair.api.ImageService
import com.example.designflair.model.Image
import com.example.designflair.model.Prompt

class ImageRepository(private val imageService: ImageService) {

    private val imageLiveData = MutableLiveData<Image>()
    private val upScaledImageLiveData = MutableLiveData<Image>()

    val image: LiveData<Image>
    get() = imageLiveData

    val upScaledImage: LiveData<Image>
    get() = upScaledImageLiveData

    fun clearImage(){
        imageLiveData.postValue(null)
    }

    fun clearScaledImage(){
        upScaledImageLiveData.postValue(Image(""))
    }

    suspend fun getImage(prompt: Prompt, token: String) {
        val result = imageService.getImage(prompt, token)
        if(result.body() != null) {
            imageLiveData.postValue(result.body())
        }
    }

    suspend fun getUpScaledImage(image: Image, token: String) {
        val result = imageService.getUpScaledImage(image, token)
        if(result.body() != null) {
            upScaledImageLiveData.postValue(result.body())
        }
    }

}