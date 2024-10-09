package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputHP = findViewById<EditText>(R.id.inputHP)
        val inputCP = findViewById<EditText>(R.id.inputCP)
        val inputSP = findViewById<EditText>(R.id.inputSP)
        val inputNP = findViewById<EditText>(R.id.inputNP)
        val inputOP = findViewById<EditText>(R.id.inputOP)
        val inputWP = findViewById<EditText>(R.id.inputWP)
        val inputAP = findViewById<EditText>(R.id.inputAP)

        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resetButton = findViewById<Button>(R.id.resetButton) // Додаємо кнопку "Скинути"
        val task2Button = findViewById<Button>(R.id.task2Button)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        // Обробка кнопки "Розрахувати"
        calculateButton.setOnClickListener {
            val HP = inputHP.text.toString().toDoubleOrNull() ?: 0.0
            val CP = inputCP.text.toString().toDoubleOrNull() ?: 0.0
            val SP = inputSP.text.toString().toDoubleOrNull() ?: 0.0
            val NP = inputNP.text.toString().toDoubleOrNull() ?: 0.0
            val OP = inputOP.text.toString().toDoubleOrNull() ?: 0.0
            val WP = inputWP.text.toString().toDoubleOrNull() ?: 0.0
            val AP = inputAP.text.toString().toDoubleOrNull() ?: 0.0

            val (KRS, KRG) = calculateConversionFactors(WP, AP)
            val dryMass = calculateDryMass(HP, CP, SP, NP, OP, KRS)
            val combustibleMass = calculateCombustibleMass(HP, CP, SP, NP, OP, KRG)

            // Додаємо обчислення нижчої теплоти згоряння
            val heatWorkingMass = calculateHeat(CP, HP, OP, SP) // Для робочої маси
            val heatDryMass = calculateHeat(CP * KRS, HP * KRS, OP * KRS, SP * KRS) // Для сухої маси
            val heatCombustibleMass = calculateHeat(CP * KRG, HP * KRG, OP * KRG, SP * KRG) // Для горючої маси

            resultTextView.text = """
                Суха маса палива: $dryMass
                Горюча маса палива: $combustibleMass
                
                Нижча теплота згоряння:
                - Робоча маса: $heatWorkingMass кДж/кг
                - Суха маса: $heatDryMass кДж/кг
                - Горюча маса: $heatCombustibleMass кДж/кг
            """.trimIndent()
        }

        // Обробка кнопки "Скинути"
        resetButton.setOnClickListener {
            // Очищення полів введення
            inputHP.text.clear()
            inputCP.text.clear()
            inputSP.text.clear()
            inputNP.text.clear()
            inputOP.text.clear()
            inputWP.text.clear()
            inputAP.text.clear()

            // Повернення початкового тексту у TextView
            resultTextView.text = "Тут ви побачите результати!"
        }

        task2Button.setOnClickListener {
            // Створюємо інтенцію для переходу до Task2Activity
            val intent = Intent(this, Task2Activity::class.java)
            startActivity(intent)  // Запускаємо Task2Activity
        }
    }

    private fun calculateConversionFactors(WP: Double, AP: Double): Pair<Double, Double> {
        val KRS = 100 / (100 - WP)
        val KRG = 100 / (100 - WP - AP)
        return Pair(KRS, KRG)
    }

    private fun calculateDryMass(HP: Double, CP: Double, SP: Double, NP: Double, OP: Double, KRS: Double): String {
        val HS = HP * KRS
        val CS = CP * KRS
        val SS = SP * KRS
        val NS = NP * KRS
        val OS = OP * KRS
        return "H: %.3f%%, C: %.3f%%, S: %.3f%%, N: %.3f%%, O: %.3f%%".format(HS, CS, SS, NS, OS)
    }

    private fun calculateCombustibleMass(HP: Double, CP: Double, SP: Double, NP: Double, OP: Double, KRG: Double): String {
        val HG = HP * KRG
        val CG = CP * KRG
        val SG = SP * KRG
        val NG = NP * KRG
        val OG = OP * KRG
        return "H: %.3f%%, C: %.3f%%, S: %.3f%%, N: %.3f%%, O: %.3f%%".format(HG, CG, SG, NG, OG)
    }

    // Функція для обчислення нижчої теплоти згоряння
    private fun calculateHeat(C: Double, H: Double, O: Double, S: Double): Double {
        return 337 * C + 1442 * (H - O / 8) + 93 * S
    }
}
