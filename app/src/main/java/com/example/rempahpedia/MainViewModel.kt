package com.example.rempahpedia

import android.app.Application
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val permissionGranted = MutableLiveData<Boolean>()

    // Tambahkan variabel untuk menyimpan fragment yang aktif
    private val _currentFragment = MutableLiveData<FragmentTag>()
    val currentFragment: LiveData<FragmentTag> = _currentFragment

    // Fungsi untuk mengubah fragment yang aktif
    fun setCurrentFragment(fragmentTag: FragmentTag) {
        _currentFragment.value = fragmentTag
    }

    // Enum untuk tag fragment
    enum class FragmentTag {
        HOME, SCAN, LIST
    }

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
