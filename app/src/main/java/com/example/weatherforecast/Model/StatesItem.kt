package com.example.weatherforecast.Model


import com.google.gson.annotations.SerializedName

data class StatesItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("regiao")
    val regiao: Regiao,
    @SerializedName("sigla")
    val sigla: String
)