package com.example.weatherforecast.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.Adapter.WeatherForecastAdapter
import com.example.weatherforecast.Model.Data
import com.example.weatherforecast.R
import com.example.weatherforecast.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityPresenter.ViewCallback {
    private val presenter: MainActivityPresenter by lazy { MainActivityPresenter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate(intent)

        recyclerview?.setHasFixedSize(true)
        recyclerview?.setLayoutManager(LinearLayoutManager(this))
    }


    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun setupRecycler(list: List<Data>) {
        val adapter = WeatherForecastAdapter(this, list)
        recyclerview.adapter = adapter
    }

    override fun finishWithError() {
        Toast.makeText(this, getString(R.string.cidade_nao_encontrada), Toast.LENGTH_SHORT).show()
        finish()
    }
}