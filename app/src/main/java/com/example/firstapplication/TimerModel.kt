package com.example.firstapplication

import android.os.CountDownTimer
import android.util.Log

class TimerModel {

    private var countDownTimer: CountDownTimer? = null
    var isRunning: Boolean = false
        private set

    /**
     * Запуск або перезапуск таймера.
     * @param seconds Кількість секунд для відліку.
     * @param onTick Колбек, що викликається щосекунди (передає залишок секунд).
     * @param onFinish Колбек, що викликається при завершенні відліку.
     */
    fun start(
        seconds: Long,
        onTick: (Long) -> Unit,
        onFinish: () -> Unit
    ) {
        // Якщо таймер вже працює — скасовуємо його, щоб уникнути "зомбі-таймерів"
        stop()

        val millisInFuture = seconds * 1000L

        countDownTimer = object : CountDownTimer(millisInFuture, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000L
                onTick(secondsLeft)
            }

            override fun onFinish() {
                isRunning = false
                onTick(0)
                onFinish()
                Log.d("TimerModel", "Таймер завершив роботу")
            }
        }.start()

        isRunning = true
        Log.d("TimerModel", "Таймер запущено на $seconds сек.")
    }

    /**
     * Зупинка поточного таймера.
     */
    fun stop() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            countDownTimer = null
            isRunning = false
            Log.d("TimerModel", "Таймер зупинено")
        }
    }
}