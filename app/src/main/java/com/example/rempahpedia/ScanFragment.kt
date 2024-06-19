package com.example.rempahpedia

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rempahpedia.databinding.FragmentScanBinding
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initCameraExecutor()
        viewModel.startCamera(requireContext(), binding.cameraPreview.surfaceProvider)

        binding.capture.setOnClickListener {
            takePhotoAndUpload()
        }

        binding.flash.setOnClickListener {
            viewModel.toggleFlash()
        }
    }

    private fun takePhotoAndUpload() {
        val imageCapture = viewModel.imageCapture ?: return

        val photoFile = File(
            requireContext().cacheDir,
            "${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions, viewModel.cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("ScanFragment", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    lifecycleScope.launch {
                        val compressedFile = compressImage(photoFile)
                        val multipartImage = convertFileToMultipart(compressedFile)
                        val response = viewModel.uploadImage(multipartImage)
                        if (response != null) {
                            val intent = Intent(requireContext(), DetailSpiceActivity::class.java)
                            intent.putExtra("response", response)
                            startActivity(intent)
                        }
                    }
                }
            })
    }

    private fun compressImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        val compressedFile = File(requireContext().cacheDir, "compressed_${file.name}")

        ByteArrayOutputStream().use { baos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos) // Compress to 50% quality
            FileOutputStream(compressedFile).use { fos ->
                fos.write(baos.toByteArray())
            }
        }

        return compressedFile
    }

    private fun convertFileToMultipart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.shutdownCameraExecutor()
        _binding = null
    }
}
