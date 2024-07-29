package com.saha.lifeplustenicaltest.view.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.saha.lifeplustenicaltest.databinding.ActivitySplashBinding
import com.saha.lifeplustenicaltest.view.activity.auth.LoginActivity
import com.saha.lifeplustenicaltest.view.activity.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val countDownTimer = object : CountDownTimer(3000, 1000) {

        // Callback function, fired on regular interval
        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            //Intent to login Activity
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        componentInit()
        clickListeners()
        viewModelObservers()
        getIntentData()
        initData()
    }

    private fun componentInit() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countDownTimer.start()
    }

    private fun clickListeners() {

    }

    private fun viewModelObservers() {

    }

    private fun getIntentData() {

    }

    private fun initData() {

    }
}