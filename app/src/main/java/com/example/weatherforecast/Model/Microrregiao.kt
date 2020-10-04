package com.example.weatherforecast.Model


import com.google.gson.annotations.SerializedName

data class Microrregiao(
    @SerializedName("id")
    val id: Int,
    @SerializedName("mesorregiao")
    val mesorregiao: Mesorregiao,
    @SerializedName("nome")
    val nome: String
)