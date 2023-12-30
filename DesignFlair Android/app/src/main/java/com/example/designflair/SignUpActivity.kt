package com.example.designflair

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.designflair.api.ImageService
import com.example.designflair.api.RetrofitHelper
import com.example.designflair.api.UserService
import com.example.designflair.databinding.ActivitySignInBinding
import com.example.designflair.databinding.ActivitySignUpBinding
import com.example.designflair.model.AuthRequest
import com.example.designflair.model.User
import com.example.designflair.preferences.UserPreferences
import com.example.designflair.repository.ImageRepository
import com.example.designflair.repository.UserRepository
import com.example.designflair.viewModel.MainViewModel
import com.example.designflair.viewModel.MainViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        sharedPreferences = getSharedPreferences("UserPreference", MODE_PRIVATE)

        val imageService = RetrofitHelper.getInstance().create(ImageService::class.java)
        val imageRepository = ImageRepository(imageService)

        val userService = RetrofitHelper.getInstance().create(UserService::class.java)
        val userRepository = UserRepository(userService, UserPreferences(sharedPreferences))

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(imageRepository, userRepository))[MainViewModel::class.java]
        binding.signInButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val username = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            mainViewModel.register(User(firstName, lastName, username, password))
            mainViewModel.authResult.observe(this, Observer {
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