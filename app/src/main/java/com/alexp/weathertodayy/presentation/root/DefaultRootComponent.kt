package com.alexp.weathertodayy.presentation.root

import com.alexp.weathertodayy.domain.entity.City
import com.alexp.weathertodayy.presentation.details.DefaultDetailsComponent
import com.alexp.weathertodayy.presentation.details.DefaultDetailsComponent_Factory
import com.alexp.weathertodayy.presentation.favourite.DefaultFavouriteComponent
import com.alexp.weathertodayy.presentation.search.DefaultSearchComponent
import com.alexp.weathertodayy.presentation.search.OpenReason
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable
import javax.inject.Inject


class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext")componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {


    private val navigation = StackNavigation<Config>()


    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favourite,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::child
    )


        @OptIn(DelicateDecomposeApi::class)
        private fun child(
            config:Config,
            componentContext: ComponentContext
        ):RootComponent.Child{
            return when(config)
            {
                is Config.Details -> {
                    val component = detailsComponentFactory.create(
                        city = config.city,
                        onBackClicked = {
                            navigation.pop()
                        },
                        componentContext =  componentContext
                    )
                    RootComponent.Child.Details(component)
                }
                Config.Favourite -> {
                    val component = favouriteComponentFactory.create(
                        onCityItemClicked = {
                            navigation.push(Config.Details(it))
                        },
                        onAddFavouriteClicked = {
                            navigation.push(Config.Search(OpenReason.AddToFavourite))
                        },
                        onSearchClicked = {
                            navigation.push(Config.Search(OpenReason.RegularSearch))

                        },
                        componentContext = componentContext
                    )
                    RootComponent.Child.Favourites(component)
                }
                is Config.Search -> {
                    val component = searchComponentFactory.create(
                        openReason = config.openReason,
                        onBackClicked = {
                            navigation.pop()
                        },
                        onCitySavedToFavourite = {
                            navigation.pop()
                        },
                        onForecastForCityRequested = {
                            navigation.push(Config.Details(it))
                        },
                        componentContext =  componentContext
                    )
                    RootComponent.Child.Search(component)
                }
            }
        }

    @Serializable
    private sealed interface Config{

        @Serializable
        data object Favourite:Config

        @Serializable
        data class Search(val openReason: OpenReason):Config

        @Serializable
        data class Details(val city: City) : Config
    }

    @AssistedFactory
    interface Factory{

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ):DefaultRootComponent
    }
}