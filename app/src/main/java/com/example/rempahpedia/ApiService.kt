package com.example.rempahpedia

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    fun getClassList(): Call<ClassListResponse>
}
