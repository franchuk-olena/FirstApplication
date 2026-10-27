package com.example.firstapplication
import android.app.Application
import android.util.Log


class MyApplication: Application() {

    val TAG = "XXXX"

    override fun onCreate(){
        super.onCreate()

        Log.d(TAG, "Застосунок стартував! Тут можна ініціалізувати бібліотеки")
    }
}