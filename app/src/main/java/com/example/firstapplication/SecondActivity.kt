package com.example.firstapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("SecondActivity: onCreate() викликано")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("SecondActivity: onStart() викликано")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("SecondActivity: onResume() викликано")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("SecondActivity: onPause() викликано")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("SecondActivity: onStop() викликано")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("SecondActivity: onDestroy() викликано")
    }
}