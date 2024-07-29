package com.saha.lifeplustenicaltest.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast

sealed class ScreenState<T>(
    val data: T? = null, val message: String? = null
) {
    class Success<T>(data: T?, message: String? = null) : ScreenState<T>(data, message = message)
    class Loading<T>(data: T? = null) : ScreenState<T>(data)
    class Error<T>(message: String) : ScreenState<T>(message = message)

}

inline fun <reified T> Context.handleScreenState(
    screenState: ScreenState<T>,
    loadingDialog: LoadingDialog? = null,
    loadingAction: () -> Unit = {
        loadingDialog?.let {
            it.show()
        }
    },
    errorAction: (String?) -> Unit = { s: String? ->
        Log.d("ScreenState", "handleScreenState: error")

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        loadingDialog?.hide()

    },
    successAction: (T, String?) -> Unit = { _, msg ->
        msg?.let {
            Log.d("ScreenState", "handleScreenState: $msg")
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
) {
    when (screenState) {
        is ScreenState.Loading -> loadingAction()
        is ScreenState.Error -> errorAction(screenState.message)
        is ScreenState.Success -> successAction(screenState.data!!, screenState.message)
    }
}


