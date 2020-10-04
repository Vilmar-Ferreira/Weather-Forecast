package com.example.weatherforecast.Retrofit

import com.example.weatherforecast.Model.City
import com.example.weatherforecast.Model.DataModel
import com.example.weatherforecast.Model.States
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyAPI {
    @GET("forecast/daily")
    fun getWeatherForecast(@Query("city") city: String?, @Query("country") country: String?, @Query("lang") lang: String?, @Query("key") key: String?): Observable<DataModel?>?

    @get:GET("localidades/estados/")
    val states: Observable<States?>?

    @GET("localidades/estados/{UF}/municipios")
    fun getCityes(@Path("UF") name: String): Observable<City?>?
}