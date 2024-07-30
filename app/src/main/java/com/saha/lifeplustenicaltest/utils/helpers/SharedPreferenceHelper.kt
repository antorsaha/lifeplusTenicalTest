package com.saha.lifeplustenicaltest.utils.helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


object SharedPreferenceHelper {
    private const val MY_PREFS_FILENAME = "lifePlusSharedPreference"
    private const val USER_NAME = "userName"
    fun saveUserName(context: Context, userName: String) {
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE)
                .edit()
        editor.putString(USER_NAME, userName)
        editor.apply()
    }

    fun getUserName(context: Context): String {
        val preferences: SharedPreferences =
            context.getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE)
        return preferences.getString(USER_NAME, "") ?: ""
    }
}