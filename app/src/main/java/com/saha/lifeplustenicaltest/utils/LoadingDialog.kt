package com.saha.lifeplustenicaltest.utils

import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.view.Window
import android.widget.ImageView
import com.saha.lifeplustenicaltest.R

class LoadingDialog(var activity: Activity) {
    var dialog: Dialog? = null
    private var loadImageGif: Int = 0
    private var cancelable = false

    init {
        createDialog()
    }


    fun setCancelable(state: Boolean) {
        cancelable = state
    }

    private fun createDialog(){

        dialog = Dialog(activity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //inflate the layout
        dialog!!.setContentView(R.layout.custom_loading_dialog_layout)
        //setup cancelable, default=false
        dialog!!.setCancelable(cancelable)

    }

    fun show() {

        if (!activity.isFinishing && dialog != null && !dialog!!.isShowing && !activity.isDestroyed ) {
            dialog!!.show()
        }else{
            Log.d("LoadingDialog", "show: not")
        }
    }

    fun hide() {
        Log.d("LoadingDialog", "hide: ")

        if (dialog != null && dialog!!.isShowing) {
            if(!activity.isFinishing && !activity.isDestroyed){

                dialog!!.dismiss()
            }
        }
    }

    val isLoading: Boolean
        get() = dialog!!.isShowing


}