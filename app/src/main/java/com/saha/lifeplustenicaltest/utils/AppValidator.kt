package com.saha.lifeplustenicaltest.utils

import android.content.Context
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber

object AppValidator {
    fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"
        val passwordMatcher = Regex(passwordPattern)
        return passwordMatcher.matches(password)
    }

    fun isValidNumber(context: Context, phoneNumber: String): Boolean {
        val phoneUtil = PhoneNumberUtil.createInstance(context)

        try {
            return phoneUtil.isValidNumber(
                getPhoneNumberProtoBufferFromInternationPhoneNumber(
                    context,
                    phoneNumber
                )
            )
        } catch (e: Exception) {
            return false
        }
    }

    private fun getPhoneNumberProtoBufferFromInternationPhoneNumber(
        context: Context,
        phoneNumber: String
    ): Phonenumber.PhoneNumber? {
        val phoneUtil = PhoneNumberUtil.createInstance(context)

        return try {
            // Phone number must begin with '+'
            phoneUtil.parse(phoneNumber, "")
        } catch (e: NumberParseException) {
            // Handle parsing errors (e.g., invalid phone number)
            null
        } catch (_: Exception) {
            null
        }
    }


}