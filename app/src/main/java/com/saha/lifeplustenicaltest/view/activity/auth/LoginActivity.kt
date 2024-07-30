package com.saha.lifeplustenicaltest.view.activity.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivityLoginBinding
import com.saha.lifeplustenicaltest.utils.LoadingDialog
import com.saha.lifeplustenicaltest.utils.handleScreenState
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper
import com.saha.lifeplustenicaltest.view.activity.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel
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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this, ViewModelInstanceHelper(
                this.application,
                RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[AuthViewModel::class.java]
        loadingDialog = LoadingDialog(this)

    }

    private fun listener() {
        binding.userName.editText.doOnTextChanged { text, start, before, count ->
            viewModel.userName = text.toString()
            checkLoginButtonStatus()
        }

        binding.password.editText.doOnTextChanged { text, start, before, count ->
            viewModel.password = text.toString()
            checkLoginButtonStatus()
        }
    }

    private fun clickListeners() {
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }

        binding.btnLogin.btn.setOnClickListener {
            val userName = binding.userName.editText.text.toString()
            val password = binding.password.editText.text.toString()
            viewModel.login(userName, password)
        }

    }

    private fun viewModelObservers() {
        viewModel.loginResponse.observe(this) {
            handleScreenState(
                it,
                loadingDialog,
                successAction = { data, msg ->
                    Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                }
            )
        }

    }

    private fun getIntentData() {

    }

    private fun initData() {

    }

    private fun checkLoginButtonStatus() {
        binding.btnLogin.btn.isEnabled =
            viewModel.userName.isNotEmpty() && viewModel.password.isNotEmpty()
    }
}