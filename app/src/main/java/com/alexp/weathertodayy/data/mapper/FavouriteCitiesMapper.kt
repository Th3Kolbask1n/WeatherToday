package com.alexp.weathertodayy.data.mapper

import com.alexp.weathertodayy.data.local.model.CityDbModel
import com.alexp.weathertodayy.domain.entity.City


fun City.toDbModel() : CityDbModel = CityDbModel(id,name,country)

fun CityDbModel.toEntity() : City = City(id,name,country)

fun List<CityDbModel>.toEntities() : List<City> = map { it.toEntity()}