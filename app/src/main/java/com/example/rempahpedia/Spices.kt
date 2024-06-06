package com.example.rempahpedia

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Spice(
    val name: String,
    val latinName: String,
    val photo: Int
) : Parcelable