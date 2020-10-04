package com.example.weatherforecast.Model


import com.google.gson.annotations.SerializedName

data class Regiao(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("sigla")
    val sigla: String
)