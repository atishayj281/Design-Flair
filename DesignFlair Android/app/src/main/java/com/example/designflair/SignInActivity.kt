package com.example.designflair

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.designflair.api.ImageService
import com.example.designflair.api.RetrofitHelper
import com.example.designflair.api.UserService
import com.example.designflair.databinding.ActivitySignInBinding
import com.example.designflair.model.AuthRequest
import com.example.designflair.preferences.UserPreferences
import com.example.designflair.repository.ImageRepository
import com.example.designflair.repository.UserRepository
import com.example.designflair.viewModel.MainViewModel
import com.example.designflair.viewModel.MainViewModelFactory
import retrofit2.create
import java.util.prefs.Preferences

class SignInActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        sharedPreferences = getSharedPreferences("UserPreference", MODE_PRIVATE)

        val imageService = RetrofitHelper.getInstance().create(ImageService::class.java)
        val imageRepository = ImageRepository(imageService)

        val userService = RetrofitHelper.getInstance().create(UserService::class.java)
        val userRepository = UserRepository(userService, UserPreferences(sharedPreferences))

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(imageRepository, userRepository))[MainViewModel::class.java]
        val authRequest = AuthRequest()
        binding.authRequest = authRequest
        binding.signInButton.setOnClickListener {
            val username = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            mainViewModel.generateToken(AuthRequest(username, password))
            mainViewModel.authResult.observe(this, Observer {
                Log.e("Result", it.toString())
                if(it != null) {
                    mainViewModel.setToken(it.token)
                    startMainActivity()
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getToken()
        mainViewModel.token.observe(this, Observer {
            if(it.isNotEmpty()) {
                startMainActivity()
            }
        })
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}