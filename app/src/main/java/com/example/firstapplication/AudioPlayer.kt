package com.example.firstapplication

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

class AudioPlayer : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.d("AudioPlayer: Відтворення фонового аудіо відновлено")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.d("AudioPlayer: Відтворення фонового аудіо поставлено на паузу")
    }
}