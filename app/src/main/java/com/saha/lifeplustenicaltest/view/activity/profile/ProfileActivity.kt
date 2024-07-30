package com.saha.lifeplustenicaltest.view.activity.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivityProfileBinding
import com.saha.lifeplustenicaltest.utils.handleScreenState
import com.saha.lifeplustenicaltest.utils.helpers.SharedPreferenceHelper
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        componentInit()
        clickListeners()
        viewModelObservers()
        getIntentData()
        initData()

    }

    private fun componentInit() {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, ViewModelInstanceHelper(
                this.application,
                RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[ProfileViewModel::class.java]

    }

    private fun clickListeners() {
        binding.appBar.backButton.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun viewModelObservers() {
        viewModel.userResponse.observe(this) {
            handleScreenState(
                it,
                successAction = { data, msg ->
                    binding.setName(data.name)
                    binding.setUserName(data.userName)
                    binding.phone = data.phoneNumber
                }
            )
        }
    }

    private fun getIntentData() {

    }

    private fun initData() {
        viewModel.getUser(SharedPreferenceHelper.getUserName(this))
    }
}