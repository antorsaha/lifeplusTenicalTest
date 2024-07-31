package com.saha.lifeplustenicaltest.view.activity.searchDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.saha.lifeplustenicaltest.data.model.ResponseSearchItem
import com.saha.lifeplustenicaltest.data.repo.Repository

class ScheduleRideDetailsViewModel (private val repository: Repository, application: Application) :
    AndroidViewModel(application) {
        var intentData: ResponseSearchItem? = null
}