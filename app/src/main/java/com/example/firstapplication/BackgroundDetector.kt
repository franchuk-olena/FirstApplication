package com.example.firstapplication

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

class BackgroundDetector : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.d("Застосунок перейшов у FOREGROUND (видимий на екрані)")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.d("Застосунок перейшов у BACKGROUND (згорнутий у фон)")
    }
}