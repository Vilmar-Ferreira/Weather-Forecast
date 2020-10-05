package com.example.weatherforecast.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecast.R
import com.example.weatherforecast.presenter.CityActivityPresenter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_city.*
import org.jetbrains.anko.startActivity


class CityActivity : AppCompatActivity(), CityActivityPresenter.ViewCallback, OnMapReadyCallback {
    private val presenter: CityActivityPresenter by lazy { CityActivityPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        presenter.onCreate()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        spinnerState.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                presenter.getCityes(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        spinnerCity.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                presenter.setSelectedCity(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        btnShow.setOnClickListener {
            presenter.onClickShow()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        presenter.onMapReady(googleMap)
    }


    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun getSelectedState(): Int {
        return spinnerState.selectedItemPosition
    }

    override fun getSelectedCity(): Int {
        return spinnerCity.selectedItemPosition
    }

    override fun showMainActivity(city: String?, state: String?) {
        startActivity<MainActivity>("city" to city, "state" to state)
    }

    override fun setSpinnerStates(states: MutableList<String>) {
        val dataAdapter = ArrayAdapter(this,
                R.layout.spinner_item, states)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerState.setAdapter(dataAdapter)
    }

    override fun setSpinnerCities(cities: MutableList<String>) {
        val dataAdapter = ArrayAdapter(this,
                R.layout.spinner_item, cities)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCity.setAdapter(dataAdapter)
    }

    override fun setSpinnerStatePosition(i: Int) {
        spinnerState.setSelection(i)
    }

    override fun setSpinnerCityPosition(i: Int) {
        spinnerCity.setSelection(i)
    }
}