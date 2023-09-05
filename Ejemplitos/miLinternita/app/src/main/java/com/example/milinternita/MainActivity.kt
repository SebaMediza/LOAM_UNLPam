package com.example.milinternita

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cameraManager: CameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val buttonOn: Button = findViewById(R.id.buttonOn)
        val buttonOff: Button = findViewById(R.id.buttonOff)
        val text: TextView = findViewById(R.id.mainText)
        val buttonExit: Button = findViewById(R.id.exitButton)
        val buttonGoTo: Button = findViewById(R.id.goToPage2)

        buttonOn.setOnClickListener{
            cameraManager.setTorchMode("0", true)
            text.text = "Antorcha ON"
        }

        buttonOff.setOnClickListener{
            cameraManager.setTorchMode("0", false)
            text.text = "Antorcha OFF"
        }

        buttonExit.setOnClickListener{
            cameraManager.setTorchMode("0", false)
            finishAffinity()
        }

        buttonGoTo.setOnClickListener{
            val intento = Intent(this, MainActivity2::class.java)
            startActivity(intento)
        }

    }
}