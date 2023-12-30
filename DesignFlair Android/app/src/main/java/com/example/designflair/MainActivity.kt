package com.example.designflair

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.designflair.api.ImageService
import com.example.designflair.api.RetrofitHelper
import com.example.designflair.api.UserService
import com.example.designflair.databinding.ActivityMainBinding
import com.example.designflair.model.Designs
import com.example.designflair.model.Prompt
import com.example.designflair.model.Rooms
import com.example.designflair.preferences.UserPreferences
import com.example.designflair.repository.ImageRepository
import com.example.designflair.repository.UserRepository
import com.example.designflair.viewModel.MainViewModel
import com.example.designflair.viewModel.MainViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var token: String = ""
    private var rooms = Rooms()
    private var designs = Designs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val imageService = RetrofitHelper.getInstance().create(ImageService::class.java)
        val imageRepository = ImageRepository(imageService)

        sharedPreferences = getSharedPreferences("UserPreference", MODE_PRIVATE)
        val userService = RetrofitHelper.getInstance().create(UserService::class.java)
        val userPreferences = UserPreferences(sharedPreferences)
        val userRepository = UserRepository(userService, userPreferences)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(imageRepository, userRepository))[MainViewModel::class.java]
        mainViewModel.getToken()
        mainViewModel.token.observe(this, Observer {
            token = it
        })
        binding.generateButton.setOnClickListener {
            binding.imageView.setImageURI(null)
            binding.loadingProgressBar.visibility = View.VISIBLE
            val prompt =
                "A 4K photo of a ${binding.roomSpinner.text}, ${binding.designSpinner.text} design, ${binding.otherDescriptionEditText.text}"

            Log.e("Prompt", prompt)
            mainViewModel.getImage(Prompt(prompt))
            mainViewModel.image.observe(this, Observer {
                if(it != null && it.data.isNotEmpty()) {
                    Log.e("Image", it.data.toString())
                    setImage(it.data)
                    binding.loadingProgressBar.visibility = View.GONE
                }
            })
        }

        val roomAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.dropdown_menu_popup_item, rooms.list)
        roomAdapter.setDropDownViewResource(R.layout.dropdown_selected_item)
        binding.roomSpinner.setAdapter(roomAdapter)

        val designAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.dropdown_menu_popup_item, designs.list)
        designAdapter.setDropDownViewResource(R.layout.dropdown_selected_item)
        binding.designSpinner.setAdapter(designAdapter)

    }

    private fun setImage(blob: String) {
        val url = "data:image/png;base64,$blob"
        Glide.with(this).load(url).into(binding.imageView)
        mainViewModel.clearImage()

    }

    private fun setUpScaledImage(blob: String) {
        val url = "data:image/png;base64,$blob"
        Glide.with(this).load(url).into(binding.upscaledImageView)
    }

}