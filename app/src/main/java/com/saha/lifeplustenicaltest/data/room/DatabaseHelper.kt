package com.saha.lifeplustenicaltest.data.room

import android.content.Context
import com.saha.lifeplustenicaltest.data.model.User

object DatabaseHelper {
    private var initialized = false
    private var database: DatabaseDao? = null


    fun initialize(context: Context) {
        if (!initialized) {
            database = Database.getDatabase(context)?.databaseDao()
            initialized = true
        }
    }

    fun getUser(): User? {
        return database?.getUser()?.value
    }

    fun saveUser(user: User) {
        database?.saveUser(user)
    }

    fun deleteUser(user: User) {
        database?.deleteUser(user)
    }
}