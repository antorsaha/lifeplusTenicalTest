package com.saha.lifeplustenicaltest

import android.app.Application
import com.saha.lifeplustenicaltest.data.room.Database
import com.saha.lifeplustenicaltest.data.room.DatabaseHelper

class MyApplication: Application() {

    val database by lazy {
        Database.getDatabase(this)?.databaseDao()
    }


    override fun onCreate() {
        super.onCreate()

        DatabaseHelper.initialize(this)
    }
}