package com.alexp.weathertodayy.domain.repository

import com.alexp.weathertodayy.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    val favouriteCities: Flow<List<City>>

    fun observeIsFavourite(cityId:Int): Flow<Boolean>

    suspend fun addToFavourite(city: City)

    suspend fun removeFromFavourite(cityId: Int)
}