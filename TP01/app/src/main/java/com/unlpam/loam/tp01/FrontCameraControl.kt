package com.unlpam.loam.tp01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FrontCameraControl : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front_camera_control)

        val goToMainMenu :Button = findViewById(R.id.goToMainMenuFrontCamera)
        goToMainMenu.setOnClickListener{
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
        }
    }
}