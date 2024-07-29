package com.saha.lifeplustenicaltest.data.room

import android.content.Context
import android.util.Log
import com.saha.lifeplustenicaltest.data.model.User

object DatabaseHelper {
    private const val TAG = "DatabaseHelper"

    private var initialized = false
    private var database: DatabaseDao? = null


    fun initialize(context: Context) {
        if (!initialized) {
            database = Database.getDatabase(context)?.databaseDao()
            initialized = true
        }
    }

    fun getUser(userName: String): User? {
        if (database != null){
            val data = database?.getUser(userName)
            return data
        }else{
            Log.d(TAG, "getUser: database is null")
        }

        return null

    }

    fun saveUser(user: User) {
        database?.saveUser(user)
    }

    fun deleteUser(user: User) {
        database?.deleteUser(user)
    }
}