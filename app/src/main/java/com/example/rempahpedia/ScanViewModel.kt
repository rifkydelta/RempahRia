package com.example.rempahpedia

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanViewModel : ViewModel() {
    lateinit var cameraExecutor: ExecutorService

    fun initCameraExecutor() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun shutdownCameraExecutor() {
        cameraExecutor.shutdown()
    }

    fun startCamera(
        context: Context,
        surfaceProvider: Preview.SurfaceProvider
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    context as androidx.lifecycle.LifecycleOwner, cameraSelector, preview
                )
            } catch (exc: Exception) {
                Log.e("ScanViewModel", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }
}
