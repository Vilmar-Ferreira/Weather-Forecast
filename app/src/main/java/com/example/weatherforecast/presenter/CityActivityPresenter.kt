package com.example.weatherforecast.presenter

import com.example.weatherforecast.Model.City
import com.example.weatherforecast.Model.States
import com.example.weatherforecast.Model.StatesItem
import com.example.weatherforecast.Retrofit.MyAPI
import com.example.weatherforecast.Retrofit.RetrofitClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class CityActivityPresenter(private val view: ViewCallback) {

    lateinit var myAPI: MyAPI
    var compositeDisposable = CompositeDisposable()
    private lateinit var mMap: GoogleMap

    var states: States? = null
    var cityList: City? = null

    fun onCreate() {
        val retrofit = RetrofitClient.getInstance("https://servicodados.ibge.gov.br/api/v1/")
        myAPI = retrofit.create(MyAPI::class.java)
        fetchData()
    }

    fun onStop() {
        compositeDisposable.clear()
    }

    private fun fetchData() {
        myAPI.states
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { states ->
                    displayData(states)
                }?.let { compositeDisposable.add(it) }
    }

    private fun displayData(states: States?) {
        this.states = states
        val statesStr: MutableList<String> = ArrayList()

        Collections.sort(this.states, object : Comparator<StatesItem?> {
            override fun compare(p0: StatesItem?, p1: StatesItem?): Int {
                return p0?.nome!!.compareTo(p1?.nome!!, ignoreCase = true)
            }
        })

        if (this.states != null) this.states!!.forEach {
            statesStr.add(it.nome)
        }

        view.setSpinnerStates(statesStr)
        view.setSpinnerStatePosition(13)
    }

    fun getCityes(position: Int) {
        states?.let { it ->
            myAPI.getCityes(it[position].sigla)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe { city ->
                        setCities(city)
                        if (it[position].sigla == "PR") {
                            view.setSpinnerCityPosition(93)
                        }
                    }?.let { compositeDisposable.add(it) }
        }
    }

    fun setCities(city: City?) {
        this.cityList = city
        val citiesStr: MutableList<String> = ArrayList()

        if (city != null) city.forEach {
            citiesStr.add(it.nome)
        }

        view.setSpinnerCities(citiesStr)
    }

    fun onClickShow() {
        val state = states?.get(view.getSelectedState())?.sigla
        val city = cityList?.get(view.getSelectedCity())?.nome

        view.showMainActivity(city, state)
    }

    fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val latLng = LatLng(-25.4277800, -49.2730600)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latLng.latitude, latLng.longitude), 9.0f))
    }

    fun setSelectedCity(position: Int) {
        val city = this.cityList?.get(position)
        city?.microrregiao
    }

    interface ViewCallback {
        fun getSelectedState(): Int
        fun getSelectedCity(): Int
        fun showMainActivity(city: String?, state: String?)
        fun setSpinnerStates(states: MutableList<String>)
        fun setSpinnerCities(cities: MutableList<String>)
        fun setSpinnerStatePosition(i: Int)
        fun setSpinnerCityPosition(i: Int)
    }
}