package com.saha.lifeplustenicaltest.data.repo

import com.saha.lifeplustenicaltest.data.model.User


interface Repository {

    suspend fun saveUser(user: User): Result<Unit>
    suspend fun getUser(userName: String): User?
}