package com.example.rempahpedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailSpiceViewModel(application: Application) : AndroidViewModel(application) {
    val spiceName = MutableLiveData<String?>()
    val latinName = MutableLiveData<String>()
    val photoUrl = MutableLiveData<String>()
    val benefit = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val uniqueFact = MutableLiveData<String>()
    val similar = MutableLiveData<Int?>()

    fun loadSpiceDetails(
        name: String?,
        latinName: String?,
        photoUrl: String?,
        benefit: String?,
        description: String?,
        uniqueFact: String?,
        similar: Int?
    ) {
        viewModelScope.launch {
            spiceName.value = name
            this@DetailSpiceViewModel.latinName.value = latinName
            this@DetailSpiceViewModel.photoUrl.value = photoUrl
            this@DetailSpiceViewModel.benefit.value = benefit
            this@DetailSpiceViewModel.description.value = description
            this@DetailSpiceViewModel.uniqueFact.value = uniqueFact
            this@DetailSpiceViewModel.similar.value = similar
        }
    }
}
