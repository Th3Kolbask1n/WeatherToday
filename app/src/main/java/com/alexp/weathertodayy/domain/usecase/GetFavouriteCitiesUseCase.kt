package com.alexp.weathertodayy.domain.usecase

import com.alexp.weathertodayy.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteCitiesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke() = repository.favouriteCities
}