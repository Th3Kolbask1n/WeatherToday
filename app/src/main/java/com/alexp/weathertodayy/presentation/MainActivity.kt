package com.alexp.weathertodayy.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alexp.weathertodayy.data.network.api.ApiFactory
import com.alexp.weathertodayy.presentation.ui.theme.WeatherTodayyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val apiService = ApiFactory.apiService

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("1",apiService.loadCurrentWeather("London").toString())
            Log.d("1",apiService.loadForecast("London").toString())
            Log.d("1",apiService.searchCity("London").toString())

        }
        setContent {
            WeatherTodayyTheme {


            }
        }
    }
}
