package com.example.weatherforecast.Model


import com.google.gson.annotations.SerializedName

data class Mesorregiao(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("UF")
    val uF: UF
)