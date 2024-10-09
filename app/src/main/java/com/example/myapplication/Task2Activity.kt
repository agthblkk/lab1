package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Task2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)

        // Поля введення
        val inputCarbon = findViewById<EditText>(R.id.inputCarbon)
        val inputHydrogen = findViewById<EditText>(R.id.inputHydrogen)
        val inputOxygen = findViewById<EditText>(R.id.inputOxygen)
        val inputSulfur = findViewById<EditText>(R.id.inputSulfur)
        val inputWaterContent = findViewById<EditText>(R.id.inputWaterContent)
        val inputAshContent = findViewById<EditText>(R.id.inputAshContent)
        val inputLowerHeatingValue = findViewById<EditText>(R.id.inputLowerHeatingValue)

        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val resetButton = findViewById<Button>(R.id.resetButton1)
        val returnToCalculator1 = findViewById<Button>(R.id.returnToCalculator1)

        // Обробка натискання на кнопку "Розрахувати"
        calculateButton.setOnClickListener {
            // Отримуємо введені значення
            val carbon = inputCarbon.text.toString().toDoubleOrNull() ?: 0.0
            val hydrogen = inputHydrogen.text.toString().toDoubleOrNull() ?: 0.0
            val oxygen = inputOxygen.text.toString().toDoubleOrNull() ?: 0.0
            val sulfur = inputSulfur.text.toString().toDoubleOrNull() ?: 0.0
            val waterContent = inputWaterContent.text.toString().toDoubleOrNull() ?: 0.0
            val ashContent = inputAshContent.text.toString().toDoubleOrNull() ?: 0.0
            val lowerHeatingValue = inputLowerHeatingValue.text.toString().toDoubleOrNull() ?: 0.0

            // Розрахунки для робочої маси
            val workingCarbon = carbon * (100 - waterContent - ashContent) / 100
            val workingHydrogen = hydrogen * (100 - waterContent - ashContent) / 100
            val workingOxygen = oxygen * (100 - waterContent - ashContent) / 100
            val workingSulfur = sulfur * (100 - waterContent - ashContent) / 100

            // Розрахунок нижчої теплоти згоряння для робочої маси
            val lowerHeatingValueWorking = lowerHeatingValue * (100 - waterContent - ashContent) / 100

            // Виведення результатів
            resultTextView.text = """
                Робоча маса мазуту:
                Вуглець: %.3f%%
                Водень: %.3f%%
                Кисень: %.3f%%
                Сірка: %.3f%%
                Нижча теплота згоряння: %.3f МДж/кг
            """.trimIndent().format(workingCarbon, workingHydrogen, workingOxygen, workingSulfur, lowerHeatingValueWorking)
        }
        resetButton.setOnClickListener {
            // Очищення полів введення
            inputCarbon.text.clear()
            inputHydrogen.text.clear()
            inputOxygen.text.clear()
            inputSulfur.text.clear()
            inputWaterContent.text.clear()
            inputAshContent.text.clear()
            inputLowerHeatingValue.text.clear()

            // Повернення початкового тексту у TextView
            resultTextView.text = "Тут ви побачите результати!"
        }

        returnToCalculator1.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)  // Запускаємо MainActivity
        }
    }
}
