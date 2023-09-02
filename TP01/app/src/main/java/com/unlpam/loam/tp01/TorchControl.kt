package com.unlpam.loam.tp01

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast


class TorchControl : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_torch_control)

        val goToMainMenu: Button = findViewById(R.id.goToMainMenuTorch)
        val cameraManager: CameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val onOff: Button = findViewById(R.id.onOffTorch)
        var toggle = false
        onOff.setOnClickListener{
            if(!toggle){
                toggle = true
                try {
                    cameraManager.setTorchMode("0", true)
                }catch (e: Exception){
                    Toast.makeText(this@TorchControl, "Fallo al Activar el Flash, $e", Toast.LENGTH_SHORT).show()
                }
            }else{
                toggle = false
                cameraManager.setTorchMode("0", false)
            }
        }
        goToMainMenu.setOnClickListener{
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
        }
    }
}