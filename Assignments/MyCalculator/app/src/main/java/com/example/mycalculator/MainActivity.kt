package com.example.mycalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    // TextView to display the input and result
    private lateinit var tvInput: TextView

    // Variables to hold input and operators
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_landscape)

        tvInput = findViewById(R.id.tvInput)

        // Set initial display to "0"
        tvInput.text = "0"

        // Numeric buttons
        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)

        // Operator buttons
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnSubtract: Button = findViewById(R.id.btnSubtract)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnDivide: Button = findViewById(R.id.btnDivide)
        val btnPercent: Button = findViewById(R.id.btnPercent)

        // Other buttons
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnBackspace: Button = findViewById(R.id.btnBackspace)
        val btnDecimal: Button = findViewById(R.id.btnDecimal)
        val btnEquals1: Button = findViewById(R.id.btnEquals1)  // First equal button
        val btnEquals2: Button = findViewById(R.id.btnEquals2)  // Second equal button

        // Set button click listeners
        btn0.setOnClickListener { onDigit("0") }
        btn1.setOnClickListener { onDigit("1") }
        btn2.setOnClickListener { onDigit("2") }
        btn3.setOnClickListener { onDigit("3") }
        btn4.setOnClickListener { onDigit("4") }
        btn5.setOnClickListener { onDigit("5") }
        btn6.setOnClickListener { onDigit("6") }
        btn7.setOnClickListener { onDigit("7") }
        btn8.setOnClickListener { onDigit("8") }
        btn9.setOnClickListener { onDigit("9") }

        btnAdd.setOnClickListener { onOperator("+") }
        btnSubtract.setOnClickListener { onOperator("-") }
        btnMultiply.setOnClickListener { onOperator("*") }
        btnDivide.setOnClickListener { onOperator("/") }
        btnPercent.setOnClickListener { onOperator("%") }

        btnClear.setOnClickListener { onClear() }
        btnBackspace.setOnClickListener { onBackspace() }
        btnDecimal.setOnClickListener { onDecimalPoint() }
        btnEquals1.setOnClickListener { onEqual() }  // First equal button action
        btnEquals2.setOnClickListener { onEqual() }  // Second equal button action
    }

    // Appends the digit pressed to the input or replaces the initial "0"
    @SuppressLint("SetTextI18n")
    private fun onDigit(digit: String) {
        // Check if the input length is less than 15 digits
        if (tvInput.text.length < 14) {
            if (stateError) {
                tvInput.text = digit
                stateError = false
            } else if (tvInput.text.toString() == "0") {
                // Replace "0" with the first digit
                tvInput.text = digit
            } else {
                tvInput.append(digit)
            }
            lastNumeric = true
        } else {
            // Optional: Show an error message or visual feedback for max input reached
            tvInput.text = "Error = Max 14 digits calculations allowed."
        }
    }


    // Appends the operator pressed to the input
    private fun onOperator(operator: String) {
        if (lastNumeric && !stateError) {
            tvInput.append(operator)
            lastNumeric = false
            lastDot = false
        }
    }

    // Appends the decimal point to the input
    private fun onDecimalPoint() {
        if (lastNumeric && !stateError && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    // Clears the input and resets to "0"
    private fun onClear() {
        tvInput.text = "0"
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    // Deletes the last character and resets to "0" if all are deleted
    private fun onBackspace() {
        val text = tvInput.text.toString()
        if (text.isNotEmpty() && text != "0") {
            tvInput.text = text.dropLast(1)
        }
        if (tvInput.text.isEmpty()) {
            tvInput.text = "0"
        }
    }

    // Evaluates the input
    @SuppressLint("SetTextI18n")
    private fun onEqual() {
        if (lastNumeric && !stateError) {
            try {
                val expression = tvInput.text.toString().replace("%", "*0.01")
                val result = evaluate(expression)
                tvInput.text = result.toString()
            } catch (e: Exception) {
                tvInput.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

    // Parses and evaluates the expression
    private fun evaluate(expression: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < expression.length) expression[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < expression.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+'.code) -> x += parseTerm() // addition
                        eat('-'.code) -> x -= parseTerm() // subtraction
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*'.code) -> x *= parseFactor() // multiplication
                        eat('/'.code) -> x /= parseFactor() // division
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor() // unary plus
                if (eat('-'.code)) return -parseFactor() // unary minus

                val x: Double
                val startPos = pos
                if (eat('('.code)) { // parentheses
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) {
                    while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                    x = expression.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }

                return x
            }
        }.parse()
    }

    // Handle screen rotation and save the input state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current input and result
        outState.putString("inputText", tvInput.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore the saved input and result
        val inputText = savedInstanceState.getString("inputText")
        if (inputText != null) {
            tvInput.text = inputText
        }
    }

}
