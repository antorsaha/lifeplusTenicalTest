package com.saha.lifeplustenicaltest.data.repo

import androidx.lifecycle.MutableLiveData
import com.saha.lifeplustenicaltest.data.model.User


interface Repository {

    suspend fun saveUser(user: User)
    suspend fun getUser():User?
}