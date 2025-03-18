package com.alexp.weathertodayy.domain.usecase

import com.alexp.weathertodayy.domain.repository.FavouriteRepository
import com.alexp.weathertodayy.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityId: Int) = repository.getWeather(cityId)
}