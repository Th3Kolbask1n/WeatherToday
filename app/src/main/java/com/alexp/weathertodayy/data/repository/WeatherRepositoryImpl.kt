package com.alexp.weathertodayy.data.repository

import com.alexp.weathertodayy.data.mapper.toEntity
import com.alexp.weathertodayy.data.network.api.ApiService
import com.alexp.weathertodayy.domain.entity.Forecast
import com.alexp.weathertodayy.domain.entity.Weather
import com.alexp.weathertodayy.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {
    override suspend fun getWeather(cityId: Int): Weather {
        return apiService.loadCurrentWeather("$PREFIX_CITY_ID$cityId").toEntity()
    }

    override suspend fun getForecast(cityId: Int): Forecast {
        return  apiService.loadForecast("$PREFIX_CITY_ID$cityId").toEntity()
    }


    private companion object {

        private const val PREFIX_CITY_ID = "id:"
    }
}