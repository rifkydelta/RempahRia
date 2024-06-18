package com.example.rempahpedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailSpiceViewModel(application: Application) : AndroidViewModel(application) {
    val spiceName = MutableLiveData<String>()
    val latinName = MutableLiveData<String>()
    val photoResId = MutableLiveData<Int>()

    fun loadSpiceDetails(name: String?, latinName: String?, photo: Int) {
        viewModelScope.launch {
            spiceName.value = name
            this@DetailSpiceViewModel.latinName.value = latinName
            photoResId.value = photo
        }
    }
}
