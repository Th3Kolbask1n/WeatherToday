package com.alexp.weathertodayy.presentation.favourite

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.alexp.weathertodayy.presentation.favourite.FavouriteStore.Intent
import com.alexp.weathertodayy.presentation.favourite.FavouriteStore.Label
import com.alexp.weathertodayy.presentation.favourite.FavouriteStore.State

internal interface FavouriteStore : Store<Intent, State, Label> {

    sealed interface Intent {
    }

    data class State(val todo:Unit)

    sealed interface Label {
    }
}

internal class FavouriteStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): FavouriteStore =
        object : FavouriteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavouriteStore",
            initialState = State(Unit),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = State(Unit)
    }
}
