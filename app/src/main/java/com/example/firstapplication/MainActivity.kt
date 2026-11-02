package com.example.firstapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapplication.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Створюємо екземпляри обзерверів (Частини 1 та 2)
    private val audioPlayer = AudioPlayer()
    private val analyticsTracker = AnalyticsTracker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Підписуємо обох спостерігачів на життєвий цикл Activity
        lifecycle.addObserver(audioPlayer)
        lifecycle.addObserver(analyticsTracker)

        Timber.d("MainActivity: onCreate() викликано")

        // Кнопка для відкриття SecondActivity
        binding.btnOpenSecond.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("MainActivity: onStart() викликано")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity: onResume() викликано")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("MainActivity: onPause() викликано")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("MainActivity: onStop() викликано")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("MainActivity: onDestroy() викликано")
    }
}