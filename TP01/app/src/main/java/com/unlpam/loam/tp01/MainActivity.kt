package com.unlpam.loam.tp01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goToTorch: Button = findViewById(R.id.goToTorch)
        val goToGPS: Button = findViewById(R.id.goToMap)
        val goToFrontCamera: Button = findViewById(R.id.goToFrontCamera)
        val goToRearCamera: Button = findViewById(R.id.goToRearCamera)
        val exitButton: Button = findViewById(R.id.exitApp)
        val goToSound: Button = findViewById(R.id.goToSound)

        goToTorch.setOnClickListener{
            val intento = Intent(this, TorchControl::class.java)
            startActivity(intento)
        }
        goToGPS.setOnClickListener{
            val intento = Intent(this, GPSControl::class.java)
            startActivity(intento)
        }
        goToFrontCamera.setOnClickListener{
            val intento = Intent(this, FrontCameraControl::class.java)
            startActivity(intento)
        }
        goToRearCamera.setOnClickListener{
            val intento = Intent(this, RearCameraControl::class.java)
            startActivity(intento)
        }
        goToSound.setOnClickListener{
            val intento = Intent(this, SoundControl::class.java)
            startActivity(intento)
        }

        exitButton.setOnClickListener{
            finishAffinity()
        }
    }
}