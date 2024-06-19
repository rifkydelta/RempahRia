package com.example.rempahpedia

import com.google.gson.annotations.SerializedName

data class ClassListResponse(
    @SerializedName("class_list")
    val classList: Map<String, Spices>
)

data class Spices(
    @SerializedName("benefit")
    val benefit: String,
    @SerializedName("cientific_name")
    val cientificName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url1")
    val imageUrl1: String,
    @SerializedName("image_url2")
    val imageUrl2: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("unique_fact")
    val uniqueFact: String
)
