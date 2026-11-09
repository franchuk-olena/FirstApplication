package com.example.firstapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private val timerModel = TimerModel()

    // Константа за замовчуванням
    private val defaultTimeSeconds = 30L

    // Приватні MutableLiveData для внутрішньої зміни стану
    private val _timerLiveData = MutableLiveData<Long>(defaultTimeSeconds)
    private val _isTimerRunning = MutableLiveData<Boolean>(false)

    // Публічні незмінні LiveData для спостереження з View
    val timerLiveData: LiveData<Long> get() = _timerLiveData
    val isTimerRunning: LiveData<Boolean> get() = _isTimerRunning

    /**
     * Запуск таймера із заданою кількістю секунд.
     * Якщо стрічка порожня або неефективна — використовується значення за замовчуванням.
     */
    fun startTimer(userInputSeconds: String) {
        val seconds = userInputSeconds.toLongOrNull() ?: defaultTimeSeconds

        _isTimerRunning.value = true
        _timerLiveData.value = seconds

        timerModel.start(
            seconds = seconds,
            onTick = { secondsLeft ->
                _timerLiveData.postValue(secondsLeft)
            },
            onFinish = {
                _isTimerRunning.postValue(false)
            }
        )
    }

    /**
     * Скидання таймера до початкового стану.
     */
    fun resetTimer() {
        timerModel.stop()
        _isTimerRunning.value = false
        _timerLiveData.value = defaultTimeSeconds
    }

    override fun onCleared() {
        super.onCleared()
        // Зупиняємо таймер при знищенні ViewModel (наприклад, при закритті Activity)
        timerModel.stop()
        Log.d("TimerViewModel", "ViewModel знищено (onCleared викликано), таймер скасовано")
    }
}