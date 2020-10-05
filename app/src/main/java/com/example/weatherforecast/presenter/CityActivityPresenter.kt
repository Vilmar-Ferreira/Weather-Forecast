package com.example.weatherforecast.presenter

import com.example.weatherforecast.Model.City
import com.example.weatherforecast.Model.States
import com.example.weatherforecast.Retrofit.MyAPI
import com.example.weatherforecast.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class CityActivityPresenter(private val view: ViewCallback) {

    lateinit var myAPI: MyAPI
    var compositeDisposable = CompositeDisposable()

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

        if (states != null) states.forEach {
            statesStr.add(it.nome)
        }

        Collections.sort(statesStr, object : Comparator<String?> {
            override fun compare(p0: String?, p1: String?): Int {
                return p0!!.compareTo(p1!!, ignoreCase = true)
            }
        })

        view.setSpinnerStates(statesStr)
    }

    fun getCityes(position: Int) {
        states?.let {
            myAPI.getCityes(it.get(position).sigla)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe { city ->
                        setCities(city)
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

    interface ViewCallback {
        fun getSelectedState(): Int
        fun getSelectedCity(): Int
        fun showMainActivity(city: String?, state: String?)
        fun setSpinnerStates(states: MutableList<String>)
        fun setSpinnerCities(cities: MutableList<String>)
    }
}