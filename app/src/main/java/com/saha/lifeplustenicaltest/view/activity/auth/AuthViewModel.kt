package com.saha.lifeplustenicaltest.view.activity.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saha.lifeplustenicaltest.data.model.User
import com.saha.lifeplustenicaltest.data.repo.Repository
import com.saha.lifeplustenicaltest.utils.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
    private val TAG = "AuthViewModel"

    val registerResponse: MutableLiveData<ScreenState<Boolean>> = MutableLiveData()
    val loginResponse: MutableLiveData<ScreenState<User>> = MutableLiveData()

    //variable from signup activity
    var name: String = ""
    var userName: String = ""
    var password: String = ""
    var phoneNumber: String = ""

    fun register() {

        val user = User(
            name = name,
            userName = userName,
            password = password,
            phoneNumber = phoneNumber
        )

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.saveUser(user)

            if (result.isSuccess) {
                registerResponse.postValue(ScreenState.Success(true, "Register Successful"))
            } else {
                registerResponse.postValue(
                    ScreenState.Error(
                        result.exceptionOrNull()?.message ?: "Error registering user"
                    )
                )
            }
        }
    }

    fun login(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getUser(userName)

            Log.d(TAG, "login: data $data")
            if (data == null) {
                loginResponse.postValue(ScreenState.Error("User not found"))
            } else {
                if (data.password == password) {
                    loginResponse.postValue(ScreenState.Success(data))
                } else {
                    loginResponse.postValue(ScreenState.Error("Password do not match"))
                }
            }
        }
    }
}