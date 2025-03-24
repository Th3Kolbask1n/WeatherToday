package com.alexp.weathertodayy.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alexp.weathertodayy.WeatherApp
import com.alexp.weathertodayy.data.network.api.ApiFactory
import com.alexp.weathertodayy.domain.usecase.ChangeFavouriteStateUseCase
import com.alexp.weathertodayy.domain.usecase.SearchCityUseCase
import com.alexp.weathertodayy.presentation.root.DefaultRootComponent
import com.alexp.weathertodayy.presentation.root.RootContent
import com.alexp.weathertodayy.presentation.ui.theme.WeatherTodayyTheme
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory:DefaultRootComponent.Factory



    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as WeatherApp).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RootContent(component = rootComponentFactory.create(defaultComponentContext()))
        }
    }
}
