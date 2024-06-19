package com.example.rempahpedia

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanViewModel : ViewModel() {
    lateinit var cameraExecutor: ExecutorService
    var imageCapture: ImageCapture? = null
    private var flashEnabled = false

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

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    context as androidx.lifecycle.LifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Log.e("ScanViewModel", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    fun toggleFlash(context: Context) {
        flashEnabled = !flashEnabled
        imageCapture?.flashMode = if (flashEnabled) {
            ImageCapture.FLASH_MODE_ON
        } else {
            ImageCapture.FLASH_MODE_OFF
        }

        val message = if (flashEnabled) {
            "Flash is ON"
        } else {
            "Flash is OFF"
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    suspend fun uploadImage(multipartImage: MultipartBody.Part): String? {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addPart(multipartImage)
                    .build()

                // Logging request body
                Log.d("ScanViewModel", "Request Body: $requestBody")

                val request = Request.Builder()
                    .url("https://backend-rempahpedia-fix-nzrrwl5fkq-et.a.run.app/predict")
                    .post(requestBody)
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    return@withContext response.body?.string()
                }
            } catch (e: Exception) {
                Log.e("ScanViewModel", "Upload failed", e)
                return@withContext null
            }
        }
    }
}
