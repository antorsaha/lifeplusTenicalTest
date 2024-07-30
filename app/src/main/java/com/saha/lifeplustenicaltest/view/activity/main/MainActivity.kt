package com.saha.lifeplustenicaltest.view.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.saha.lifeplustenicaltest.BuildConfig
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.R
import com.saha.lifeplustenicaltest.data.model.User
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivityLoginBinding
import com.saha.lifeplustenicaltest.databinding.ActivityMainBinding
import com.saha.lifeplustenicaltest.utils.LoadingDialog
import com.saha.lifeplustenicaltest.utils.handleScreenState
import com.saha.lifeplustenicaltest.utils.helpers.SharedPreferenceHelper
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper
import com.saha.lifeplustenicaltest.view.activity.auth.AuthViewModel
import com.saha.lifeplustenicaltest.view.activity.auth.SignupActivity
import com.saha.lifeplustenicaltest.view.activity.profile.ProfileActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        componentInit()
        listener()
        clickListeners()
        viewModelObservers()
        getIntentData()
        initData()

    }

    private fun componentInit() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(
            this, ViewModelInstanceHelper(
                this.application,
                RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[MainViewModel::class.java]
        loadingDialog = LoadingDialog(this)

    }

    private fun listener() {

    }

    private fun clickListeners() {
        binding.ivProfile.setOnClickListener{
            startActivity(Intent(
                this,ProfileActivity::class.java
            ))
        }

    }

    private fun viewModelObservers() {


    }

    private fun getIntentData() {

    }

    private fun initData() {

    }
}