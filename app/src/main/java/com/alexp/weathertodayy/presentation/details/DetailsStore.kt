package com.alexp.weathertodayy.presentation.details

import android.text.BoringLayout
import com.alexp.weathertodayy.domain.entity.City
import com.alexp.weathertodayy.domain.entity.Forecast
import com.alexp.weathertodayy.domain.usecase.ChangeFavouriteStateUseCase
import com.alexp.weathertodayy.domain.usecase.GetForecastUseCase
import com.alexp.weathertodayy.domain.usecase.ObserveFavouriteStateUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.alexp.weathertodayy.presentation.details.DetailsStore.Intent
import com.alexp.weathertodayy.presentation.details.DetailsStore.Label
import com.alexp.weathertodayy.presentation.details.DetailsStore.State
import com.alexp.weathertodayy.presentation.favourite.FavouriteStore

import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailsStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object ClickBack : Intent

        data object ClickChangeFavouriteStatus : Intent
    }

    data class State(
        val city: City,
        val isFavourite: Boolean,
        val forecastState: ForecastState
    ) {

        sealed interface ForecastState {

            data object Initial : ForecastState

            data object Loading : ForecastState

            data object Error : ForecastState

            data class Loaded(val forecast: Forecast) : ForecastState
        }
    }


    sealed interface Label {

        data object ClickBack : Label

    }
}

 class DetailsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getForecastUseCase: GetForecastUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val observeFavouriteStateUseCase: ObserveFavouriteStateUseCase
) {

    fun create(city: City): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(
                city = city,
                isFavourite = false,
                forecastState = State.ForecastState.Initial
            ),
            bootstrapper = BootstrapperImpl(city),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data class FavouriteStatusChange(val isFavourite: Boolean) : Action

        data class ForecastLoaded(val forecast: Forecast) : Action

        data object ForecastStartLoading : Action

        data object ForeCastLoadingError : Action

    }

    private sealed interface Msg {

        data class FavouriteStatusChange(val isFavourite: Boolean) : Msg

        data class ForecastLoaded(val forecast: Forecast) : Msg

        data object ForecastStartLoading : Msg

        data object ForeCastLoadingError : Msg
    }

    private inner class BootstrapperImpl(
        private val city: City
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeFavouriteStateUseCase(city.id).collect {
                    dispatch(Action.FavouriteStatusChange(it))
                }
            }
            scope.launch {
                dispatch(Action.ForecastStartLoading)
                try {
                    val forecast = getForecastUseCase(city.id)
                    dispatch(Action.ForecastLoaded(forecast))
                } catch (e: Exception) {
                    dispatch(Action.ForeCastLoadingError)
                }
            }
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent) {
            when (intent) {

                Intent.ClickBack -> publish(Label.ClickBack)
                Intent.ClickChangeFavouriteStatus -> {
                    scope.launch {
                        val state = state()
                        if (state.isFavourite) {
                            changeFavouriteStateUseCase.removeFromFavourite(state.city.id)
                        } else {
                            changeFavouriteStateUseCase.addToFavourite(state.city)

                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action) {
            when (action) {
                is Action.FavouriteStatusChange -> {
                    dispatch(Msg.FavouriteStatusChange(action.isFavourite))
                }


                is Action.ForecastLoaded -> {

                    dispatch(Msg.ForecastLoaded(action.forecast))
                }


                is Action.ForeCastLoadingError -> {
                    dispatch(Msg.ForeCastLoadingError)

                }

                is Action.ForecastStartLoading -> {
                    dispatch(Msg.ForecastStartLoading)

                }
            }

        }

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg) = when (msg) {
            is Msg.FavouriteStatusChange -> {
                copy(isFavourite = msg.isFavourite)
            }

            Msg.ForeCastLoadingError -> {
                copy(forecastState = State.ForecastState.Error)
            }

            is Msg.ForecastLoaded -> {
                copy(forecastState = State.ForecastState.Loaded(msg.forecast))

            }

            Msg.ForecastStartLoading -> {
                copy(forecastState = State.ForecastState.Loading)

            }
        }
    }
}
