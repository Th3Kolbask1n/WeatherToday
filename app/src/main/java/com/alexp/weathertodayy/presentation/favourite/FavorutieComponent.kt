package com.alexp.weathertodayy.presentation.favourite

import com.alexp.weathertodayy.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavorutieComponent {

    val model: StateFlow<FavouriteStore.State>

    fun onClickSearch()

    fun onClickAddFavourite()

    fun onCityItemClick(city: City)

}