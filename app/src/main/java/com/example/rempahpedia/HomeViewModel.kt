package com.example.rempahpedia

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _spices = MutableLiveData<List<Spice>>()
    val spices: LiveData<List<Spice>> get() = _spices

    private val _funFacts = MutableLiveData<List<String>>()
    val funFacts: LiveData<List<String>> get() = _funFacts

    init {
        // Set default fun facts
        _funFacts.value = listOf(
            "Fun Fact 1",
            "Fun Fact 2",
            "Fun Fact 3",
            "Fun Fact 4"
        )
    }

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
