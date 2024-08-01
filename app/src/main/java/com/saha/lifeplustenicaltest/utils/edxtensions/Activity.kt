package com.saha.lifeplustenicaltest.utils.edxtensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat

fun Activity.showShotToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}