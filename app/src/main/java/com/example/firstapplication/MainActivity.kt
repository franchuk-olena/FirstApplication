package com.example.firstapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Отримуємо TimerViewModel
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserTracker.register(this)

        // 1. Спостереження за значенням таймера
        viewModel.timerLiveData.observe(this) { seconds ->
            binding.tvTimer.text = seconds.toString()
        }

        // 2. Блокування кнопок під час відліку
        viewModel.isTimerRunning.observe(this) { isRunning ->
            binding.btnStart.isEnabled = !isRunning
            binding.etSecondsInput.isEnabled = !isRunning
        }

        // 3. Обробники кліків
        binding.btnStart.setOnClickListener {
            viewModel.startTimer(binding.etSecondsInput.text.toString())
        }

        binding.btnReset.setOnClickListener {
            viewModel.resetTimer()
        }

        binding.btnOpenSecond.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UserTracker.unregister(this)
    }
}