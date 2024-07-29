package com.saha.lifeplustenicaltest.view.activity.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivitySignupBinding
import com.saha.lifeplustenicaltest.utils.AppValidator
import com.saha.lifeplustenicaltest.utils.LoadingDialog
import com.saha.lifeplustenicaltest.utils.handleScreenState
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        componentInit()
        viewListeners()
        clickListeners()
        viewModelObservers()
        getIntentData()
        initData()
    }

    private fun componentInit() {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, ViewModelInstanceHelper(
                this.application,
                RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[AuthViewModel::class.java]

        loadingDialog = LoadingDialog(this)

        binding.btnRegister.btn.isEnabled = false
    }

    private fun viewListeners() {
        binding.name.editText.doOnTextChanged { text, start, before, count ->
            viewModel.name = text.toString()

            if (viewModel.name.isEmpty()) {
                binding.name.editTextInputLayout.error = "Required"
            } else {
                binding.name.editTextInputLayout.error = null
            }
            checkRegisterButtonEnableStatus()
        }

        binding.userName.editText.doOnTextChanged { text, start, before, count ->
            viewModel.userName = text.toString()

            if (viewModel.userName.isEmpty()) {
                binding.userName.editTextInputLayout.error = "Required"
            } else {
                binding.userName.editTextInputLayout.error = null
            }

            checkRegisterButtonEnableStatus()
        }

        binding.password.editText.doOnTextChanged { text, start, before, count ->
            viewModel.password = text.toString()

            if (AppValidator.isPasswordValid(viewModel.password)) {
                binding.password.editTextInputLayout.error = null
            } else {
                binding.password.editTextInputLayout.error = "Enter a Valid password"
            }
            checkRegisterButtonEnableStatus()
        }

        binding.phoneNumber.editText.doOnTextChanged { text, start, before, count ->
            viewModel.phoneNumber = text.toString()

            if (AppValidator.isValidNumber(this, viewModel.phoneNumber)) {
                binding.phoneNumber.editTextInputLayout.error = null
            } else {
                binding.phoneNumber.editTextInputLayout.error = "Enter a valid Number"
            }

            checkRegisterButtonEnableStatus()
        }
    }

    private fun clickListeners() {
        binding.btnRegister.btn.setOnClickListener {
            viewModel.register()
        }
    }

    private fun viewModelObservers() {
        viewModel.registerResponse.observe(this) {
            handleScreenState(
                it,
                loadingDialog
            )
        }
    }

    private fun getIntentData() {

    }

    private fun initData() {

    }

    private fun checkRegisterButtonEnableStatus() {
        binding.btnRegister.btn.isEnabled =
            viewModel.name.isNotEmpty() && viewModel.userName.isNotEmpty() && AppValidator.isPasswordValid(
                viewModel.password
            ) && AppValidator.isValidNumber(this, viewModel.phoneNumber)
    }
}