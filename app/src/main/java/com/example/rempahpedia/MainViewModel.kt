package com.example.rempahpedia

import android.app.Application
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val permissionGranted = MutableLiveData<Boolean>()

    fun checkPermission(permission: String) {
        viewModelScope.launch {
            val isGranted = ContextCompat.checkSelfPermission(
                getApplication(),
                permission
            ) == PackageManager.PERMISSION_GRANTED

            permissionGranted.value = isGranted
        }
    }

    fun requestPermission(permission: String, requestPermissionLauncher: () -> Unit) {
        viewModelScope.launch {
            requestPermissionLauncher()
        }
    }

    fun showToastMessage(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show()
    }
}
