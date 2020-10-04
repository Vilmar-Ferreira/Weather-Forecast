package com.example.weatherforecast.Model


import com.google.gson.annotations.SerializedName

data class UF(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("regiao")
    val regiao: RegiaoX,
    @SerializedName("sigla")
    val sigla: String
)