package com.example.milinternita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btnVolver: Button = findViewById(R.id.buttonBack)
        btnVolver.setOnClickListener{
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
        }


    }
}