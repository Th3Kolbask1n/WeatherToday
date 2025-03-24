package com.alexp.weathertodayy

import android.app.Application
import com.alexp.weathertodayy.di.ApplicationComponent
import com.alexp.weathertodayy.di.DaggerApplicationComponent


class WeatherApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}