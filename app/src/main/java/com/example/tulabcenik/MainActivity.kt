package com.example.tulabcenik

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inputs for 3D Printing
        val printingTimeHours = findViewById<EditText>(R.id.printing_time_hours)
        val printingTimeMinutes = findViewById<EditText>(R.id.printing_time_minutes)
        val printingWeight = findViewById<EditText>(R.id.printing_weight)
        val printingMethodGroup = findViewById<RadioGroup>(R.id.printing_method_group)

        // Inputs for CNC Sawing
        val sawingStitches = findViewById<EditText>(R.id.sawing_stitches)
        val vlizelinTop = findViewById<CheckBox>(R.id.cb_vlizelin_top)
        val vlizelinBottom = findViewById<CheckBox>(R.id.cb_vlizelin_bottom)

        // Inputs for Laser Cutting
        val laserTimeHours = findViewById<EditText>(R.id.laser_time_hours)
        val laserTimeMinutes = findViewById<EditText>(R.id.laser_time_minutes)

        // Buttons
        val calculateButton = findViewById<Button>(R.id.btn_calculate)
        val clearButton = findViewById<Button>(R.id.btn_clear)

        // Result TextView
        val resultView = findViewById<TextView>(R.id.tv_result)

        calculateButton.setOnClickListener {
            var totalPrice = 0.0

            // 3D Printing Price Calculation
            val timeHours = printingTimeHours.text.toString().toDoubleOrNull() ?: 0.0
            val timeMinutes = printingTimeMinutes.text.toString().toDoubleOrNull() ?: 0.0
            val totalTime = timeHours + (timeMinutes / 60)

            val weight = printingWeight.text.toString().toDoubleOrNull() ?: 0.0
            val selectedMethodId = printingMethodGroup.checkedRadioButtonId

            if (selectedMethodId != -1 && (totalTime > 0 || weight > 0)) {
                when (selectedMethodId) {
                    R.id.rb_sls -> {
                        // SLS pricing - weight in kg
                        totalPrice += 100 + (500 * weight) + (100 * totalTime)
                    }
                    R.id.rb_fdm -> {
                        // FDM pricing - weight in kg
                        totalPrice += 50 + (400 * weight/1000) + (45 * totalTime)
                    }
                    R.id.rb_sla -> {
                        // SLA pricing - weight in mL (convert directly)
                        totalPrice += 100 + (3.8 * weight) + (45 * totalTime) // 3800 CZK/kg is 3.8 CZK/mL
                    }
                }
            }

            // CNC Sawing Price Calculation
            val stitches = sawingStitches.text.toString().toIntOrNull() ?: 0
            if (stitches > 0 || vlizelinTop.isChecked || vlizelinBottom.isChecked) {
                var sawingPrice = 50 + (0.015 * stitches)
                if (vlizelinTop.isChecked) sawingPrice += 30
                if (vlizelinBottom.isChecked) sawingPrice += 30
                totalPrice += sawingPrice
            }

            // Laser Cutting Price Calculation
            val laserHours = laserTimeHours.text.toString().toDoubleOrNull() ?: 0.0
            val laserMinutes = laserTimeMinutes.text.toString().toDoubleOrNull() ?: 0.0
            val totalLaserTime = laserHours + (laserMinutes / 60)

            if (totalLaserTime > 0) {
                totalPrice += 50 + (totalLaserTime * 6 * 60) // Laser cutting in CZK/min
            }

            // Display the result
            resultView.text = if (totalPrice > 0) {
                "Total Price: %.2f CZK".format(totalPrice)
            } else {
                "Please provide valid inputs."
            }
        }

        // Clear button action
        clearButton.setOnClickListener {
            // Clear all inputs and result
            printingTimeHours.text.clear()
            printingTimeMinutes.text.clear()
            printingWeight.text.clear()
            printingMethodGroup.clearCheck()

            sawingStitches.text.clear()
            vlizelinTop.isChecked = false
            vlizelinBottom.isChecked = false

            laserTimeHours.text.clear()
            laserTimeMinutes.text.clear()
            resultView.text = ""
        }
    }
}
