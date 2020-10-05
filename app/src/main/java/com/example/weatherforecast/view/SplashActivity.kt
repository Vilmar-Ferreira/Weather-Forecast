package com.example.weatherforecast.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherforecast.R
import com.example.weatherforecast.presenter.SplashActivityPresenter
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity(), SplashActivityPresenter.ViewCallback {

    private val presenter: SplashActivityPresenter by lazy { SplashActivityPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.onCreate()
    }

    override fun showHomeActivity() {
        startActivity<CityActivity>()
        finish()
    }
}