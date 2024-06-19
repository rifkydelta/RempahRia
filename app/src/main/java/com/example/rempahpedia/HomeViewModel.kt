package com.example.rempahpedia

import RetrofitClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _spices = MutableLiveData<List<Spices>>()
    val spices: LiveData<List<Spices>> get() = _spices

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

    fun loadSpices() {
        RetrofitClient.apiService.getClassList().enqueue(object : Callback<ClassListResponse> {
            override fun onResponse(
                call: Call<ClassListResponse>,
                response: Response<ClassListResponse>
            ) {
                if (response.isSuccessful) {
                    val classListResponse = response.body()
                    val items = classListResponse?.classList?.values?.toList() ?: emptyList()
                    _spices.postValue(items)
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<ClassListResponse>, t: Throwable) {
                // Handle error
            }
        })
    }
}