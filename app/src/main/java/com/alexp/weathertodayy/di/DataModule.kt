package com.alexp.weathertodayy.di

import android.content.Context
import com.alexp.weathertodayy.data.local.db.FavouriteCitiesDao
import com.alexp.weathertodayy.data.local.db.FavouriteDatabase
import com.alexp.weathertodayy.data.network.api.ApiFactory
import com.alexp.weathertodayy.data.network.api.ApiService
import com.alexp.weathertodayy.data.repository.FavouriteRepositoryImpl
import com.alexp.weathertodayy.data.repository.SearchRepositoryImpl
import com.alexp.weathertodayy.data.repository.WeatherRepositoryImpl
import com.alexp.weathertodayy.domain.repository.FavouriteRepository
import com.alexp.weathertodayy.domain.repository.SearchRepository
import com.alexp.weathertodayy.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
interface  DataModule{


    @[ApplicationScope Binds]
    fun bindFavouriteRepository(impl:FavouriteRepositoryImpl) : FavouriteRepository

    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl:WeatherRepositoryImpl) : WeatherRepository

    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl) : SearchRepository

    companion object
    {
        @[ApplicationScope Provides]
        fun provideApiService():ApiService = ApiFactory.apiService

        @[ApplicationScope Provides]
        fun provideFavouriteDatabase(context: Context) : FavouriteDatabase{
            return FavouriteDatabase.getInstance(context)
        }

        @[ApplicationScope Provides]
        fun provideFavouriteCitiesDao(database: FavouriteDatabase): FavouriteCitiesDao{
            return database.favouriteCitiesDao()
        }
    }

}