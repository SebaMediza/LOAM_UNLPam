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

class RearCameraControl : AppCompatActivity() {

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
    // Función para tomar una foto con la cámara
    private fun takePhoto() {
        // Comprueba si la variable 'imageCapture' es nula; si es nula, la función retorna
        val imageCapture = imageCapture ?: return
        // Crea un objeto 'photoFile' que representa el archivo de la foto a tomar
        val photoFile = File(outputDirectory, SimpleDateFormat(Constants.FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis()) + ".jpg")
        // Configura las opciones de salida del archivo de la imagen capturada
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        // Captura la imagen utilizando 'imageCapture' con las opciones de salida definidas
        imageCapture.takePicture(outputOption, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                // Función llamada cuando la imagen se guarda con éxito
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // Obtiene la URI del archivo guardado
                    val savedUri = Uri.fromFile(photoFile)
                    // Crea un mensaje de notificación
                    val msg = "Photo Saved"
                    // Muestra una notificación Toast con el mensaje y la URI de la foto
                    Toast.makeText(this@RearCameraControl, "$msg $savedUri", Toast.LENGTH_LONG).show()
                }
                // Función llamada si ocurre un error al guardar la imagen
                override fun onError(exception: ImageCaptureException) {
                    // Registra un mensaje de error en el registro de la aplicación (log)
                    Log.e(Constants.TAG, "${exception.message}", exception)
                }
            })
    }

    // Función para iniciar la cámara con la cámara trasera
    private fun startCamera() {
        // Obtiene una instancia del proveedor de la cámara a través de ProcessCameraProvider
        val cameraProviderFeature = ProcessCameraProvider.getInstance(this)
        // Agrega un oyente para recibir notificaciones cuando el proveedor de la cámara esté listo
        cameraProviderFeature.addListener({
            // Obtiene la instancia del proveedor de la cámara
            val cameraProvider: ProcessCameraProvider = cameraProviderFeature.get()
            // Configura la vista previa de la cámara
            val preview = Preview.Builder().build().also { mPreview ->
                // Asocia la superficie de vista previa con la vista definida en el diseño
                mPreview.setSurfaceProvider(binding.viewFinderRearCamera.surfaceProvider)
            }
            // Configura la captura de imagen
            imageCapture = ImageCapture.Builder().build()
            // Selecciona la cámara trasera como la cámara predeterminada
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                // Desvincula todas las cámaras existentes
                cameraProvider.unbindAll()

                // Vincula la cámara seleccionada (trasera), la vista previa y la captura de imagen a un ciclo de vida específico
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                // Registra un mensaje de error en el registro de la aplicación (log) si no se puede iniciar la cámara
                Log.d(Constants.TAG, "Fail to Start Camera", e)
            }
        }, ContextCompat.getMainExecutor(this)) // Ejecuta el oyente en el subproceso principal
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