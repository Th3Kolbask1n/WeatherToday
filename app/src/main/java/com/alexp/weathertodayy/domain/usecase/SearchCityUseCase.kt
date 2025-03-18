package com.alexp.weathertodayy.domain.usecase

import com.alexp.weathertodayy.domain.repository.FavouriteRepository
import com.alexp.weathertodayy.domain.repository.SearchRepository
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend operator fun invoke(query: String) = repository.searchCities(query)
}