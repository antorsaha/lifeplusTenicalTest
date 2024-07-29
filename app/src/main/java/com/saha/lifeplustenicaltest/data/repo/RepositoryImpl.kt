package com.sikderithub.brightacademy.data.repo

import com.saha.lifeplustenicaltest.data.model.GenericResponse
import com.saha.lifeplustenicaltest.data.network.MyApi
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