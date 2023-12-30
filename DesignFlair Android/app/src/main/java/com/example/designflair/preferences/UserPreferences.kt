package com.example.designflair.preferences

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.designflair.model.User

class UserPreferences(private val sharedPreferences: SharedPreferences) {

    fun setToken(token: String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun getToken(): String {
        return sharedPreferences.getString("token", "").toString()
    }



}