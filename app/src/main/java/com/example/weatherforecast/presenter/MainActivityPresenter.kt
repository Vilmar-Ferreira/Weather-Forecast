package com.example.weatherforecast.presenter

import android.content.Intent
import com.example.weatherforecast.Model.Data
import com.example.weatherforecast.Model.DataModel
import com.example.weatherforecast.Retrofit.MyAPI
import com.example.weatherforecast.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.Normalizer
import java.util.regex.Pattern

class MainActivityPresenter(private val view: ViewCallback) {
    lateinit var myAPI: MyAPI
    var compositeDisposable = CompositeDisposable()

    fun onCreate(intent: Intent) {
        val retrofit = RetrofitClient.getInstance("https://api.weatherbit.io/v2.0/")
        myAPI = retrofit.create(MyAPI::class.java)


        intent.extras?.let {
            val city = it.getString("city")
            val state = it.getString("state")

            fetchData(city, state)
        }

    }

    private fun fetchData(city: String?, state: String?) {
        val nfdNormalizedString = Normalizer.normalize(city, Normalizer.Form.NFD);
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        val cityFormated = pattern.matcher(nfdNormalizedString).replaceAll("");
        myAPI.getWeatherForecast(cityFormated, "BR", "pt", "f85c52ec15d64e79991e3b1ed648fbb1")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnError {
                    compositeDisposable.clear()
                    view.finishWithError()
                }
                ?.subscribe { data ->
                    data?.let { displayData(it) }
                }?.let {
                    compositeDisposable.add(it)
                }
    }

    private fun displayData(dataModel: DataModel) {
        view.setupRecycler(dataModel.data)
    }

    fun onStop() {
        compositeDisposable.clear()
    }

    fun onClickItem(it: Data) {
    }

    interface ViewCallback {
        fun setupRecycler(list: List<Data>)
        fun finishWithError()
    }
}