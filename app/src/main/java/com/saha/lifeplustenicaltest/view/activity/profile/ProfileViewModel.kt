package com.saha.lifeplustenicaltest.view.activity.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saha.lifeplustenicaltest.data.model.User
import com.saha.lifeplustenicaltest.data.repo.Repository
import com.saha.lifeplustenicaltest.utils.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
    val userResponse: MutableLiveData<ScreenState<User>> = MutableLiveData()

    fun getUser(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getUser(userName)

            if (data == null) {
                userResponse.postValue(ScreenState.Error("User not found"))
            } else {

                userResponse.postValue(ScreenState.Success(data))

            }
        }
    }
}