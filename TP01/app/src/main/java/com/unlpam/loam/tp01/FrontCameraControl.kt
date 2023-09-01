package com.unlpam.loam.tp01

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.unlpam.loam.tp01.databinding.ActivityRearCameraControlBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FrontCameraControl : AppCompatActivity() {

    private lateinit var binding: ActivityRearCameraControlBinding
    private var imageCapture: ImageCapture?=null
    private var outputDirectory: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    private lateinit var cameraExecutor: ExecutorService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRearCameraControlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cameraExecutor = Executors.newSingleThreadExecutor()

        if (allPermissionGranted()){
            startCamera()
        }else{
            ActivityCompat.requestPermissions(this, Constants.REQUIRED_PERMITIONS, Constants.REQUEST_CODE_PERMISSIONS)
        }
        binding.btnTakePicRearCamera.setOnClickListener{
            takePhoto()
        }
    }
    private fun takePhoto(){
        val imageCapture = imageCapture ?: return
        val photoFile = File(outputDirectory, SimpleDateFormat(Constants.FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis()) + ".jpg")
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOption, ContextCompat.getMainExecutor(this),
            object: ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo Saved"
                    Toast.makeText(this@FrontCameraControl,"$msg $savedUri", Toast.LENGTH_LONG).show()
                }
                override fun onError(exception: ImageCaptureException) {
                    Log.e(Constants.TAG,"${exception.message}", exception)
                }
            })
    }
    private fun startCamera(){
        val cameraProviderFeature = ProcessCameraProvider.getInstance(this)
        cameraProviderFeature.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()
            val preview = Preview.Builder().build().also {mPreview ->
                mPreview.setSurfaceProvider(binding.viewFinderRearCamera.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try{
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e: Exception){
                Log.d(Constants.TAG, "Fail to Start Camera", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == Constants.REQUEST_CODE_PERMISSIONS){
            if(allPermissionGranted()){
                startCamera()
            }else{
                Toast.makeText(this,"Permissions not Granted by the User.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMITIONS.all{
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}