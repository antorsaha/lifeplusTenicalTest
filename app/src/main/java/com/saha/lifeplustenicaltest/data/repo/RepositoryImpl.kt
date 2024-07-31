package com.saha.lifeplustenicaltest.data.repo

import android.util.Log
import com.saha.lifeplustenicaltest.data.model.GenericResponse
import com.saha.lifeplustenicaltest.data.model.ResponseSearchItem
import com.saha.lifeplustenicaltest.data.model.User
import com.saha.lifeplustenicaltest.data.network.MyApi
import com.saha.lifeplustenicaltest.data.room.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RepositoryImpl(private val myApi: MyApi) : Repository {
    private val TAG = "RepositoryImpl"

    override suspend fun saveUser(user: User) :Result<Unit> {

          return DatabaseHelper.saveUser(user)

    }

    override suspend fun getUser(userName: String): User? {
        Log.d(TAG, "getUser: ${DatabaseHelper.getUser(userName)}")
        return DatabaseHelper.getUser(userName)
    }

    override suspend fun searchShow(query: String): GenericResponse<MutableList<ResponseSearchItem>> {
        return executeSafely { myApi.searchShow(query) }
    }


    private suspend fun <T : Any> executeSafely(
        block: suspend () -> Response<T>
    ): GenericResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                block().safeResponse()
            } catch (e: Exception) {

                GenericResponse(true, e.message ?: "")
            }
        }
    }

    private fun <T : Any> Response<T>.safeResponse(): GenericResponse<T> {
        return if (isSuccessful) {
            if (body()!= null){
                GenericResponse(false, data = body())
            }else {
                GenericResponse(true, "Something went wrong")
            }
        } else {

            GenericResponse(true, message())
        }
    }

}