package com.alexp.weathertodayy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favourite_cities")
data class CityDbModel(
    @PrimaryKey val id:Int,
    val name:String,
    val country:String
)
