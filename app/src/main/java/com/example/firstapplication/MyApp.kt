package com.example.firstapplication

import android.app.Application
import timber.log.Timber

class MyApp : Application() {

    // Глобальна змінна у пам'яті Application
    var startCount = 0

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}