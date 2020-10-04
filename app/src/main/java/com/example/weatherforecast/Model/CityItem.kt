package com.example.weatherforecast.Model


import com.google.gson.annotations.SerializedName

data class CityItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("microrregiao")
    val microrregiao: Microrregiao,
    @SerializedName("nome")
    val nome: String
)