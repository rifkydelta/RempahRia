package com.example.rempahpedia

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    private val _spices = MutableLiveData<List<Spice>>()
    val spices: LiveData<List<Spice>> get() = _spices

    fun loadSpices(resources: Resources) {
        val dataName = resources.getStringArray(R.array.spice_name_list)
        val dataLatinName = resources.getStringArray(R.array.spice_latin_name_list)
        val dataPhoto = resources.obtainTypedArray(R.array.spice_photo_list)
        val listSpices = ArrayList<Spice>()
        for (i in dataName.indices) {
            val spice = Spice(dataName[i], dataLatinName[i], dataPhoto.getResourceId(i, -1))
            listSpices.add(spice)
        }
        dataPhoto.recycle()
        _spices.value = listSpices
    }
}
