package com.example.assignment2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Declare variables
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnSub: Button
    private lateinit var btnMul: Button
    private lateinit var btnDiv: Button
    private lateinit var resultTextView: TextView
    private lateinit var btnJoshsCalculator: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        editText1 = findViewById(R.id.edit1)
        editText2 = findViewById(R.id.edit2)
        btnAdd = findViewById(R.id.btn_add)
        btnSub = findViewById(R.id.btn_sub)
        btnMul = findViewById(R.id.btn_mul)
        btnDiv = findViewById(R.id.btn_div)
        resultTextView = findViewById(R.id.result)
        btnJoshsCalculator = findViewById(R.id.btn_joshs_calculator)

        // Set onClick listeners for calculator buttons
        btnAdd.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnMul.setOnClickListener(this)
        btnDiv.setOnClickListener(this)

        // Set onClick listener for the Josh's Calculator button
        btnJoshsCalculator.setOnClickListener {
            // Redirect to the JoshsCalculator activity
            val intent = Intent(this@MainActivity, JoshsCalculator::class.java)
            startActivity(intent)
        }
    }

    // Handle calculator button clicks
    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        val num1 = editText1.text.toString().toDoubleOrNull()
        val num2 = editText2.text.toString().toDoubleOrNull()

        if (num1 == null || num2 == null) {
            resultTextView.text = "Please enter valid numbers"
            return
        }

        val result = when (v?.id) {
            R.id.btn_add -> num1 + num2
            R.id.btn_sub -> num1 - num2
            R.id.btn_mul -> num1 * num2
            R.id.btn_div -> {
                if (num2 != 0.0) num1 / num2 else "Cannot divide by zero"
            }
            else -> "Unknown operation"
        }

        resultTextView.text = "Result is: $result"
    }
}
