package com.saha.lifeplustenicaltest.utils.helpers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sikderithub.brightacademy.data.repo.Repository

class ViewModelInstanceHelper(
    private val application: Application,
    private val repository: Repository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {


        return when {

            /*modelClass.isAssignableFrom(RideForOtherTripMapViewModel::class.java) -> RideForOtherTripMapViewModel(
                repository, application
            ) as T*/

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
