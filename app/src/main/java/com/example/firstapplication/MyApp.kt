package com.example.firstapplication

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Ініціалізація Timber для логування в Debug-збірці
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Глобальна реєстрація спостерігача за процесом усього застосунку
        ProcessLifecycleOwner.get().lifecycle.addObserver(BackgroundDetector())
    }
}