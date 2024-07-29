package com.saha.lifeplustenicaltest.view.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saha.lifeplustenicaltest.R
import com.saha.lifeplustenicaltest.databinding.ActivityLoginBinding
import com.saha.lifeplustenicaltest.databinding.ActivitySplashBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
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

    }

    private fun clickListeners() {
        binding.tvRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }

    }

    private fun viewModelObservers() {

    }

    private fun getIntentData() {

    }

    private fun initData() {

    }
}