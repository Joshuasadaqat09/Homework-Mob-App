package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Declare variables
    lateinit var editText1: EditText
    lateinit var editText2: EditText
    lateinit var btnAdd: Button
    lateinit var btnSub: Button
    lateinit var btnMul: Button
    lateinit var btnDiv: Button
    lateinit var resultTextView: TextView

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

        // Set onClick listeners
        btnAdd.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnMul.setOnClickListener(this)
        btnDiv.setOnClickListener(this)
    }

    // Handle button clicks
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
