package com.example.firstapplication

import android.content.Context
import timber.log.Timber

object UserTracker {
    private val listeners = mutableListOf<Context>()

    fun register(context: Context) {
        listeners.add(context)
        Timber.d("Зареєстровано слухачів у UserTracker: ${listeners.size}")
    }

    fun unregister(context: Context) {
        listeners.remove(context)
        Timber.d("Видалено слухача з UserTracker. Залишилось слухачів: ${listeners.size}")
    }

    fun getListenersCount(): Int = listeners.size
}