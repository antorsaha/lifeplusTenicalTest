package com.saha.lifeplustenicaltest.data.room

import android.content.Context
import android.util.Log
import com.saha.lifeplustenicaltest.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        return if (database != null) {
            Log.d(TAG, "getUser: ${database?.getUser(userName)}")
            database?.getUser(userName)

        } else {
            Log.d(TAG, "getUser: database is null")
            null
        }
    }

    suspend fun saveUser(user: User): Result<Unit> {

        return try {
            withContext(Dispatchers.IO) {
                database?.saveUser(user)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            if (e.message?.contains("UNIQUE constraint failed") == true) {
                Result.failure(Exception("User already registered"))
            } else {
                Result.failure(e)
            }
        }

    }

    suspend fun deleteUser(user: User) {
        database?.deleteUser(user)
    }
}