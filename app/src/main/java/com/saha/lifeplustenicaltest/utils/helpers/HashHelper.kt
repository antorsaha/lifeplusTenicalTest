package com.saha.lifeplustenicaltest.utils.helpers

import at.favre.lib.crypto.bcrypt.BCrypt

object HashHelper {

    fun getHashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verifyPassword(password: String, hashPassword: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashPassword)

        return result.verified
    }

}