package com.alexp.weathertodayy.data.mapper

import com.alexp.weathertodayy.data.network.dto.WeatherCurrentDto
import com.alexp.weathertodayy.data.network.dto.WeatherDto
import com.alexp.weathertodayy.data.network.dto.WeatherForecastDto
import com.alexp.weathertodayy.domain.entity.Forecast
import com.alexp.weathertodayy.domain.entity.Weather
import java.util.Calendar
import java.util.Date

fun WeatherCurrentDto.toEntity() :Weather = current.toEntity()

fun WeatherDto.toEntity() : Weather = Weather(
    tempC = tempC,
    conditionText = condition.text,
    conditionUrl = condition.iconUrl.correctImageUrl(),
    date = date.toCalendar()
)

fun WeatherForecastDto.toEntity() = Forecast(
        currentWeather = current.toEntity(),
        upcoming = forecast.forecastDay.drop(1).map {dayDto ->
            val dayWeather = dayDto.dayWeather
            Weather(
                tempC = dayWeather.tempC,
                conditionText =  dayWeather.condition.text,
                conditionUrl = dayWeather.condition.iconUrl.correctImageUrl(),
                date = dayDto.date.toCalendar()
            )
        }
        )


private fun Long.toCalendar() =
    Calendar.getInstance().apply {
        time = Date(this@toCalendar*1000)
    }

private fun String.correctImageUrl() = "https:$this".replace(
    oldValue = "64x64",
    newValue = "128x128"
)