package com.alexp.weathertodayy.presentation.details

import com.alexp.weathertodayy.domain.entity.City
import com.alexp.weathertodayy.presentation.extensions.componentScope
import com.alexp.weathertodayy.presentation.favourite.FavouriteStoreFactory
import com.alexp.weathertodayy.presentation.search.DefaultSearchComponent
import com.alexp.weathertodayy.presentation.search.OpenReason
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultDetailsComponent @AssistedInject constructor(
    private val detailsStoreFactory: DetailsStoreFactory,
    @Assisted("city")private val city: City,
    @Assisted("onBackClicked")private val onBackClicked: () -> Unit,
    @Assisted("componentContext")componentContext: ComponentContext
) : DetailsComponent, ComponentContext by componentContext
{

    private val store = instanceKeeper.getStore {
        detailsStoreFactory.create(city)
    }

    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect{
                when(it){
                    DetailsStore.Label.ClickBack -> {
                        onBackClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailsStore.State> = store.stateFlow

    override fun onClickBack() {
        store.accept(DetailsStore.Intent.ClickBack)
    }

    override fun onClickChangeFavouriteStatus() {
        store.accept(DetailsStore.Intent.ClickChangeFavouriteStatus)
    }
    @AssistedFactory
    interface Factory{

        fun create(
            @Assisted("city")city: City,
            @Assisted("onBackClicked")onBackClicked: () -> Unit,
            @Assisted("componentContext")componentContext: ComponentContext
        ): DefaultDetailsComponent
    }
}