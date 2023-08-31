package com.unlpam.loam.tp01

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class RearCameraControl : AppCompatActivity() {
    lateinit var capReq: CaptureRequest.Builder
    lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread
    private lateinit var cameraManager: CameraManager
    lateinit var textureView: TextureView
    lateinit var cameraCaptureSession: CameraCaptureSession
    lateinit var cameraDevice: CameraDevice
    lateinit var captureRequest: CaptureRequest
    lateinit var imageReader: ImageReader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rear_camera_control)

        getPermission()

        textureView = findViewById(R.id.textureView)
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                openCamera()
            }
            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {}
            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }
            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {}
        }

        imageReader = ImageReader.newInstance(720, 1280, ImageFormat.JPEG, 1)
        imageReader.setOnImageAvailableListener(object: ImageReader.OnImageAvailableListener{
            override fun onImageAvailable(p0: ImageReader?) {
                val image = p0?.acquireLatestImage()
                val buffer = image!!.planes[0].buffer
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                val file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "img.jpeg")
                val opStream = FileOutputStream(file)
                opStream.write(bytes)
//                opStream.close()
//                image.close()
                Toast.makeText(this@RearCameraControl,"image capture", Toast.LENGTH_SHORT).show()
            }
        },handler)
        findViewById<Button>(R.id.captureButton).apply {
            capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            capReq.addTarget(imageReader.surface)
            cameraCaptureSession.capture(capReq.build(),null,null)
        }

        val volver: Button = findViewById(R.id.goToMainMenuRearCamera)
        volver.setOnClickListener {
            cameraDevice.close()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    @SuppressLint("MissingPermission")
    fun openCamera() {
        cameraManager.openCamera(cameraManager.cameraIdList[0], object : CameraDevice.StateCallback() {
                override fun onOpened(p0: CameraDevice) {
                    cameraDevice = p0
                    capReq = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                    val surface = Surface(textureView.surfaceTexture)
                    capReq.addTarget(surface)
                    cameraDevice.createCaptureSession(listOf(surface, imageReader.surface),
                        object : CameraCaptureSession.StateCallback() {
                            override fun onConfigured(p0: CameraCaptureSession) {
                                cameraCaptureSession = p0
                                cameraCaptureSession.setRepeatingRequest(capReq.build(), null, null)
                            }
                            override fun onConfigureFailed(p0: CameraCaptureSession) {}
                        },
                        handler
                    )
                }
                override fun onDisconnected(p0: CameraDevice) {}
                override fun onError(p0: CameraDevice, p1: Int) {
                    Toast.makeText(this@RearCameraControl, "La camara no funciona correctamente", Toast.LENGTH_SHORT).show()
                }
            },
            handler)
    }
    private fun getPermission() {
        val permissionList = mutableListOf<String>()
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.CAMERA)
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add((android.Manifest.permission.READ_EXTERNAL_STORAGE))
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionList.size > 0) {
            requestPermissions(permissionList.toTypedArray(), 101)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it != PackageManager.PERMISSION_GRANTED) {
                getPermission()
            }
        }
    }
}