package com.saha.lifeplustenicaltest.view.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.R
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivityLoginBinding
import com.saha.lifeplustenicaltest.databinding.ActivitySplashBinding
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        componentInit()
        clickListeners()
        viewModelObservers()
        getIntentData()
        initData()
    }

    private fun componentInit() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this, ViewModelInstanceHelper(
                this.application,
                RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[AuthViewModel::class.java]

    }

    private fun clickListeners() {
        binding.tvRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }

        binding.btnLogin.btn.setOnClickListener{
            viewModel.login()
        }

    }

    private fun viewModelObservers() {

    }

    private fun getIntentData() {

    }

    private fun initData() {

    }
}