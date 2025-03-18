package com.alexp.weathertodayy.domain.usecase

import com.alexp.weathertodayy.domain.entity.City
import com.alexp.weathertodayy.domain.repository.FavouriteRepository
import retrofit2.http.Query
import javax.inject.Inject

class ObserveFavouriteStateUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke(cityId: Int) = repository.observeIsFavourite(cityId)
}