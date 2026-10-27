package com.example.firstapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val TAG = "CalculatorApp"
    }

    private lateinit var binding: ActivityMainBinding

    private var firstNumber: Double? = null
    private var currentOperation: String? = null
    private var isNewInput: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                onNumberClick(button.text.toString())
            }
        }

        binding.btnAdd.setOnClickListener { onOperationClick("+") }
        binding.btnSubtract.setOnClickListener { onOperationClick("-") }
        binding.btnMultiply.setOnClickListener { onOperationClick("*") }
        binding.btnDivide.setOnClickListener { onOperationClick("/") }

        binding.btnEquals.setOnClickListener { onEqualsClick() }
        binding.btnClear.setOnClickListener { onClearClick() }
        binding.btnBackspace.setOnClickListener { onBackspaceClick() }
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
            // Перевірка на повторне введення крапки
            if (digit == "." && currentText.contains(".")) {
                return
            }
            binding.tvDisplay.append(digit)
        }
    }

    private fun onOperationClick(operation: String) {
        val currentText = binding.tvDisplay.text.toString()

        if (currentText == "Помилка") return

        // Ланцюгові обчислення: якщо вже є збережене перше число та операція, спочатку рахуємо їх
        if (firstNumber != null && currentOperation != null && !isNewInput) {
            val num2 = currentText.toDoubleOrNull() ?: return

            if (currentOperation == "/" && num2 == 0.0) {
                Log.w(TAG, "Спроба ділення на нуль під час ланцюгового обчислення")
                binding.tvDisplay.text = "Помилка"
                resetState()
                return
            }

            val intermediateResult = calculateResult(firstNumber!!, num2, currentOperation!!)
            firstNumber = intermediateResult
            binding.tvDisplay.text = formatResult(intermediateResult)
            Log.d(TAG, "Ланцюговий результат: $intermediateResult, наступна операція: $operation")
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
            Log.w(TAG, "Спроба ділення на нуль")
            binding.tvDisplay.text = "Помилка"
            resetState()
            return
        }

        val result = calculateResult(num1, num2, currentOperation!!)
        Log.d(TAG, "Обчислено: $num1 $currentOperation $num2 = $result")

        binding.tvDisplay.text = formatResult(result)
        resetState()
    }

    private fun onBackspaceClick() {
        val currentText = binding.tvDisplay.text.toString()

        // Якщо зафіксовано помилку або готується новий ввід, Backspace просто скидає на "0"
        if (isNewInput || currentText == "Помилка" || currentText.length <= 1) {
            binding.tvDisplay.text = "0"
            isNewInput = true
            return
        }

        // Видаляємо останній символ
        binding.tvDisplay.text = currentText.substring(0, currentText.length - 1)
    }

    private fun onClearClick() {
        Log.d(TAG, "Повне очищення калькулятора")
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