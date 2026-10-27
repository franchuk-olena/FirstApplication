package com.example.firstapplication


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val TAG = "CalculatorApp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ініціалізація View-елементів
        val etFirstNumber = findViewById<EditText>(R.id.etFirstNumber)
        val etSecondNumber = findViewById<EditText>(R.id.etSecondNumber)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        // Допоміжна функція для форматування результату без зайвого .0
        fun formatResult(value: Double): String {
            return if (value % 1.0 == 0.0) {
                value.toLong().toString()
            } else {
                value.toString()
            }
        }

        // Основна функція обчислення
        fun calculate(operation: String) {
            val firstText = etFirstNumber.text.toString().trim()
            val secondText = etSecondNumber.text.toString().trim()

            // Перевірка на порожні поля
            if (firstText.isEmpty() || secondText.isEmpty()) {
                Log.w(TAG, "Спроба виконання операції '$operation' з порожніми полями")
                tvResult.text = "Будь ласка, введіть обидва числа"
                return
            }

            val num1 = firstText.toDoubleOrNull()
            val num2 = secondText.toDoubleOrNull()

            if (num1 == null || num2 == null) {
                Log.w(TAG, "Некоректний формат введених даних: first='$firstText', second='$secondText'")
                tvResult.text = "Некоректне число!"
                return
            }

            // Перевірка на ділення на нуль
            if (operation == "/" && num2 == 0.0) {
                Log.w(TAG, "Спроба ділення на нуль: $num1 / $num2")
                tvResult.text = "Помилка: ділення на нуль неможливе!"
                return
            }

            val rawResult = when (operation) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> num1 / num2
                else -> 0.0
            }

            val formattedResult = formatResult(rawResult)
            Log.d(TAG, "Виконано операцію: $num1 $operation $num2 = $formattedResult")

            tvResult.text = "Результат: $formattedResult"
        }

        // Обробники подій для математичних кнопок
        btnAdd.setOnClickListener { calculate("+") }
        btnSubtract.setOnClickListener { calculate("-") }
        btnMultiply.setOnClickListener { calculate("*") }
        btnDivide.setOnClickListener { calculate("/") }

        // Обробник для кнопки "Очистити"
        btnClear.setOnClickListener {
            Log.d(TAG, "Очищення полів вводу та результату")
            etFirstNumber.text.clear()
            etSecondNumber.text.clear()
            tvResult.text = "Результат: "
        }
    }
}