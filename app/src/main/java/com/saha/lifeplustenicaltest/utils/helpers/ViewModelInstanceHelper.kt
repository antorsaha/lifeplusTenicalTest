package com.saha.lifeplustenicaltest.utils.helpers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saha.lifeplustenicaltest.data.repo.Repository
import com.saha.lifeplustenicaltest.view.activity.auth.AuthViewModel
import com.saha.lifeplustenicaltest.view.activity.main.MainViewModel
import com.saha.lifeplustenicaltest.view.activity.profile.ProfileViewModel
import com.saha.lifeplustenicaltest.view.activity.searchDetails.ScheduleRideDetailsViewModel

class ViewModelInstanceHelper(
    private val application: Application,
    private val repository: Repository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {


        return when {

            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                repository, application
            ) as T

            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(
                repository, application
            ) as T

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(
                repository, application
            ) as T

            modelClass.isAssignableFrom(ScheduleRideDetailsViewModel::class.java) -> ScheduleRideDetailsViewModel(
                repository, application
            ) as T


            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
