package com.unlpam.loam.tp01

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.unlpam.loam.tp01.databinding.ActivityRearCameraControlBinding
import com.unlpam.loam.tp01.databinding.ActivityTorchControlBinding

class RearCameraControl : AppCompatActivity() {

    private lateinit var binding: ActivityRearCameraControlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRearCameraControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (allPermissionGranted()){
            Toast.makeText(this,"We Have Permission", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, Constants.REQUIRED_PERMITIONS, Constants.REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMITIONS.all{
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }

}