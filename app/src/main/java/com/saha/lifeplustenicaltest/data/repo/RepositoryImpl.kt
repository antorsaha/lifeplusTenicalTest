package com.saha.lifeplustenicaltest.data.repo

import android.util.Log
import com.saha.lifeplustenicaltest.data.model.GenericResponse
import com.saha.lifeplustenicaltest.data.model.User
import com.saha.lifeplustenicaltest.data.network.MyApi
import com.saha.lifeplustenicaltest.data.room.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RepositoryImpl(private val myApi: MyApi) : Repository {
    private val TAG = "RepositoryImpl"

    /*override suspend fun userLogin(
        mobileNumber: String,
        countryCode: String
    ): GenericResponse<LoginResponse> {
        return executeSafely {
            myApi.userLogin(mobileNumber, countryCode)
        }
    }*/

    override suspend fun saveUser(user: User): Boolean {
        try {
            DatabaseHelper.saveUser(user)
            return true
        } catch (_: Exception) {
            return false
        }
    }

    override suspend fun getUser(userName: String): User? {
        Log.d(TAG, "getUser: ")
        return DatabaseHelper.getUser(userName)
    }

    private suspend fun <T : Any> executeSafely(
        block: suspend () -> Response<GenericResponse<T>>
    ): GenericResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                block().safeResponse()
            } catch (e: Exception) {

                GenericResponse(true, e.message ?: "")
            }
        }
    }

    private fun <T : Any> Response<GenericResponse<T>>.safeResponse(): GenericResponse<T> {
        return if (isSuccessful) {
            body() ?: GenericResponse(true, "Something went wrong")
        } else {

            GenericResponse(true, message())
        }
    }

}