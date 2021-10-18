package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback

interface TimerCallback {
    fun onTick(millisUntilFinished: Long)

    fun onFinish()
}