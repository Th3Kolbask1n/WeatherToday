package com.alexp.weathertodayy.domain.repository

import com.alexp.weathertodayy.domain.entity.City

interface SearchRepository {

    suspend fun searchCities(query: String) : List<City>

}