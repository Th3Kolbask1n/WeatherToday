package com.alexp.weathertodayy.data.repository

import com.alexp.weathertodayy.data.mapper.toEntities
import com.alexp.weathertodayy.data.network.api.ApiService
import com.alexp.weathertodayy.domain.entity.City
import com.alexp.weathertodayy.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): SearchRepository {
    override suspend fun searchCities(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}