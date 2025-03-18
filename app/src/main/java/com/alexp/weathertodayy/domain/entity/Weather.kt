package com.alexp.weathertodayy.domain.entity

import androidx.collection.FloatIntMap
import java.util.Calendar

class Weather(
    val tempC:Float,
    val conditionText:String,
    val conditionUrl:String,
    val date: Calendar
) {
}