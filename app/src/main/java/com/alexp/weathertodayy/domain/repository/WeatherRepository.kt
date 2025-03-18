package com.alexp.weathertodayy.domain.repository

import com.alexp.weathertodayy.domain.entity.City
import com.alexp.weathertodayy.domain.entity.Forecast
import com.alexp.weathertodayy.domain.entity.Weather
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface WeatherRepository {

    suspend fun getWeather(cityId: Int):Weather

    suspend fun getForecast(cityId: Int):Forecast


}