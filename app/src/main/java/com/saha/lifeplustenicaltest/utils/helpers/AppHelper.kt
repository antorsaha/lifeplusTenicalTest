package com.saha.lifeplustenicaltest.utils.helpers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object AppHelper {

    fun openBrowserWithURL(activity: Activity, url: String) {
        if (url.isEmpty()) {
            return
        }
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(activity, "Can not open browser with this URL: $url", Toast.LENGTH_SHORT).show()
        }
    }
}