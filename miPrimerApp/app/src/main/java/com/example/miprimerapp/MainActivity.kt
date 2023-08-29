package com.example.miprimerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonLunch: Button = findViewById(R.id.buttonLunch)
        buttonLunch.setOnClickListener{
            rollDice()
        }
        val buttonExit: Button = findViewById(R.id.exitButton)
        buttonExit.setOnClickListener(){
//            finish()
            finishAffinity()
        }

    }
    private fun rollDice(){
        val number: Int = lanzar(6)
        val result: TextView = findViewById(R.id.textView)
        result.text = number.toString()
        //Toast.makeText(this, number.toString(), Toast.LENGTH_SHORT).show()
    }
    private fun lanzar(maxNumber: Int): Int {
        return (1..maxNumber).random()
    }
}