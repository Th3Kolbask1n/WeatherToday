package com.alexp.weathertodayy.data.mapper

import com.alexp.weathertodayy.data.network.dto.CityDto
import com.alexp.weathertodayy.domain.entity.City


fun CityDto.toEntity() : City = City(id,name,country)

fun List<CityDto>.toEntities() : List<City> = map{it.toEntity()}