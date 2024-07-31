package com.saha.lifeplustenicaltest.view.activity.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saha.lifeplustenicaltest.data.model.ResponseSearchItem
import com.saha.lifeplustenicaltest.data.model.User
import com.saha.lifeplustenicaltest.data.repo.Repository
import com.saha.lifeplustenicaltest.utils.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
    private val TAG = "MainViewModel"

    val userLiveData: MutableLiveData<User> = MutableLiveData()

    val searchResult: MutableLiveData<ScreenState<MutableList<ResponseSearchItem>>> =
        MutableLiveData()

    /*fun saveUser(user: User) {
        Log.d(TAG, "saveUser: user ${user}")
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveUser(user)
        }
    }*/

    /*fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userData = repository.getUser("")

            Log.d(TAG, "getUser: data $userData")
            userData?.let { data ->
                userLiveData.postValue(data)
            }
        }
    }*/

    fun searchShows(showName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchResult.postValue(ScreenState.Loading())
            val response = repository.searchShow(showName)

            if (response.error) {
                searchResult.postValue(ScreenState.Error(response.message))
            } else {
                searchResult.postValue(ScreenState.Success(response.data))
            }
        }

    }
}