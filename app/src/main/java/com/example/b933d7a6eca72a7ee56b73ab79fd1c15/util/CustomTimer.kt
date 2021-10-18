package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util

import android.os.CountDownTimer
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.TimerCallback

class CustomTimer(spaceship: Spaceship, private val timerCallback: TimerCallback) :
    CountDownTimer(spaceship.timer * 1000, 1000) {
    override fun onTick(millisUntilFinished: Long) {
        timerCallback.onTick(millisUntilFinished)
    }

    override fun onFinish() {
        timerCallback.onFinish()
    }
}