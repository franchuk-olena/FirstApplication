package com.example.firstapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapplication.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var firstNumber: Double? = null
    private var currentOperation: String? = null
    private var isNewInput: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d("MainActivity успішно запущена!")

        setupClickListeners()
    }

    private fun setupClickListeners() {
        val numberButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9,
            binding.btnDot
        )

        for (button in numberButtons) {
            button.setOnClickListener {
                incrementAndLogClick()
                onNumberClick(button.text.toString())
            }
        }

        binding.btnAdd.setOnClickListener {
            incrementAndLogClick()
            onOperationClick("+")
        }
        binding.btnSubtract.setOnClickListener {
            incrementAndLogClick()
            onOperationClick("-")
        }
        binding.btnMultiply.setOnClickListener {
            incrementAndLogClick()
            onOperationClick("*")
        }
        binding.btnDivide.setOnClickListener {
            incrementAndLogClick()
            onOperationClick("/")
        }

        binding.btnEquals.setOnClickListener {
            incrementAndLogClick()
            onEqualsClick()
        }
        binding.btnClear.setOnClickListener {
            incrementAndLogClick()
            onClearClick()
        }
        binding.btnBackspace.setOnClickListener {
            incrementAndLogClick()
            onBackspaceClick()
        }
    }

    // Метод для збільшення змінної в MyApp та виводу через Timber
    private fun incrementAndLogClick() {
        val app = application as MyApp
        app.startCount++
        Timber.d("Поточне значення startCount: ${app.startCount}")
    }

    private fun onNumberClick(digit: String) {
        val currentText = binding.tvDisplay.text.toString()

        if (isNewInput || currentText == "0" || currentText == "Помилка") {
            if (digit == ".") {
                binding.tvDisplay.text = "0."
            } else {
                binding.tvDisplay.text = digit
            }
            isNewInput = false
        } else {
            if (digit == "." && currentText.contains(".")) {
                return
            }
            binding.tvDisplay.append(digit)
        }
    }

    private fun onOperationClick(operation: String) {
        val currentText = binding.tvDisplay.text.toString()

        if (currentText == "Помилка") return

        if (firstNumber != null && currentOperation != null && !isNewInput) {
            val num2 = currentText.toDoubleOrNull() ?: return

            if (currentOperation == "/" && num2 == 0.0) {
                Timber.w("Спроба ділення на нуль під час ланцюгового обчислення")
                binding.tvDisplay.text = "Помилка"
                resetState()
                return
            }

            val intermediateResult = calculateResult(firstNumber!!, num2, currentOperation!!)
            firstNumber = intermediateResult
            binding.tvDisplay.text = formatResult(intermediateResult)
            Timber.d("Ланцюговий результат: $intermediateResult, наступна операція: $operation")
        } else {
            firstNumber = currentText.toDoubleOrNull()
        }

        currentOperation = operation
        isNewInput = true
    }

    private fun onEqualsClick() {
        val secondText = binding.tvDisplay.text.toString()

        if (firstNumber == null || currentOperation == null || secondText == "Помилка") {
            return
        }

        val num1 = firstNumber!!
        val num2 = secondText.toDoubleOrNull() ?: return

        if (currentOperation == "/" && num2 == 0.0) {
            Timber.w("Спроба ділення на нуль")
            binding.tvDisplay.text = "Помилка"
            resetState()
            return
        }

        val result = calculateResult(num1, num2, currentOperation!!)
        Timber.d("Обчислено: $num1 $currentOperation $num2 = $result")

        binding.tvDisplay.text = formatResult(result)
        resetState()
    }

    private fun onBackspaceClick() {
        val currentText = binding.tvDisplay.text.toString()

        if (isNewInput || currentText == "Помилка" || currentText.length <= 1) {
            binding.tvDisplay.text = "0"
            isNewInput = true
            return
        }

        binding.tvDisplay.text = currentText.substring(0, currentText.length - 1)
    }

    private fun onClearClick() {
        Timber.d("Повне очищення калькулятора")
        binding.tvDisplay.text = "0"
        resetState()
    }

    private fun resetState() {
        firstNumber = null
        currentOperation = null
        isNewInput = true
    }

    private fun calculateResult(num1: Double, num2: Double, operation: String): Double {
        return when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> 0.0
        }
    }

    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}