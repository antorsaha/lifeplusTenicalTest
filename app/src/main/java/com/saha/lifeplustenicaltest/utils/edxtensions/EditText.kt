package com.saha.lifeplustenicaltest.utils.edxtensions

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText

fun EditText.trackTypingState(startTyping:() ->Unit, typingComplete: () -> Unit) {
    val TAG = this.javaClass.simpleName
    this.addTextChangedListener(object : TextWatcher {
        var timer: CountDownTimer? = null
        var isTyping = false

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            if (!isTyping){
                isTyping = true
                Log.d(TAG, "afterTextChanged: start typing")
                startTyping.invoke()
            }

            timer?.cancel()
            timer = object : CountDownTimer(1500, 1500) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    Log.d(TAG, "onFinish: typing complete")
                    isTyping = false
                    typingComplete.invoke()
                }
            }.start()
        }
    })
}
