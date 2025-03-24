package com.alexp.weathertodayy.presentation.root

import androidx.compose.runtime.Composable
import com.alexp.weathertodayy.presentation.details.DetailsContent
import com.alexp.weathertodayy.presentation.favourite.FavorutieComponent
import com.alexp.weathertodayy.presentation.favourite.FavouriteContent
import com.alexp.weathertodayy.presentation.search.SearchContent
import com.alexp.weathertodayy.presentation.ui.theme.WeatherTodayyTheme
import com.arkivanov.decompose.extensions.compose.stack.Children

@Composable

fun RootContent(
    component: RootComponent
)
{
    WeatherTodayyTheme {
        Children(stack = component.stack) {
            when(val instane = it.instance)
            {
                is RootComponent.Child.Details -> {
                    DetailsContent(component = instane.component)
                }
                is RootComponent.Child.Favourites -> {
                    FavouriteContent(component = instane.component)

                }
                is RootComponent.Child.Search -> {
                    SearchContent(component = instane.component)

                }
            }

    }



    }
}