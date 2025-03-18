package com.alexp.weathertodayy.data.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class CityDto(
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("country") val country:String
)
