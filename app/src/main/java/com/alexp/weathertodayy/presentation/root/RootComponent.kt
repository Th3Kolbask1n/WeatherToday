package com.alexp.weathertodayy.presentation.root

import com.alexp.weathertodayy.presentation.details.DetailsComponent
import com.alexp.weathertodayy.presentation.favourite.FavorutieComponent
import com.alexp.weathertodayy.presentation.search.SearchComponent
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val stack : Value<ChildStack<*,Child>>

    sealed interface  Child {
        data class Favourites(val component: FavorutieComponent) : Child

        data class Search(val component: SearchComponent) : Child

        data class Details(val component: DetailsComponent) : Child

    }
}