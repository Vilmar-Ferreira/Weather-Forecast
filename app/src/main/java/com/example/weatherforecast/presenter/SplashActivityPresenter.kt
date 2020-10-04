package com.example.weatherforecast.presenter

import android.os.Handler
import android.os.Looper

class SplashActivityPresenter(private val view: ViewCallback) {

    fun onCreate() {

        Handler(Looper.getMainLooper()).postDelayed({
            view.showHomeActivity()
        }, 2000)

    }

    interface ViewCallback {
        fun showHomeActivity()
    }
}