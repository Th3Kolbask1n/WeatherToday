package com.alexp.weathertodayy.domain.entity

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)
