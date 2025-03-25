package com.alexp.weathertodayy.presentation.details

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexp.weathertodayy.R
import com.alexp.weathertodayy.domain.entity.Forecast
import com.alexp.weathertodayy.domain.entity.Weather
import com.alexp.weathertodayy.presentation.extensions.formattedFullDate
import com.alexp.weathertodayy.presentation.extensions.formattedShortDayOfWeek
import com.alexp.weathertodayy.presentation.extensions.tempToFormattedString
import com.alexp.weathertodayy.presentation.ui.theme.CardGradients
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable

fun DetailsContent(
    component: DetailsComponent
) {
    val state by component.model.collectAsState()

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                cityName = state.city.name,
                isCityFavourite = state.isFavourite,
                onBackClick = { component.onClickBack() },
                onClickChangeFavouriteStatus = { component.onClickChangeFavouriteStatus() }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(CardGradients.gradients[2].primaryGradient) // Новый градиент
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val forecastState = state.forecastState) {
                DetailsStore.State.ForecastState.Error -> {
                    Error()
                }

                DetailsStore.State.ForecastState.Initial -> {
                    Initial()
                }

                is DetailsStore.State.ForecastState.Loaded -> {
                    Forecast(forecast = forecastState.forecast)
                }

                DetailsStore.State.ForecastState.Loading -> {
                    Loading()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    cityName: String,
    isCityFavourite: Boolean,
    onBackClick: () -> Unit,
    onClickChangeFavouriteStatus: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = cityName) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        actions = {
            IconButton(onClick = { onClickChangeFavouriteStatus() }) {
                val icon = if (isCityFavourite) {
                    Icons.Default.Star
                } else {
                    Icons.Default.StarBorder
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    )
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.background
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)

@Composable
private fun Forecast(forecast: Forecast) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.2f) // Полупрозрачный фон
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = forecast.currentWeather.conditionText,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                GlideImage(
                    modifier = Modifier.size(100.dp),
                    model = forecast.currentWeather.conditionUrl,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = forecast.currentWeather.tempC.tempToFormattedString(),
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 80.sp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = forecast.currentWeather.date.formattedFullDate(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedUpcomingWeather(upcoming = forecast.upcoming)
    }
}


@Composable
private fun AnimatedUpcomingWeather(upcoming: List<Weather>) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(animationSpec = tween(500)) + slideIn(
            animationSpec = tween(500),
            initialOffset = { IntOffset(0, it.height) }
        )
    ) {
        UpcomingWeather(upcoming = upcoming)
    }
}

@Composable
private fun UpcomingWeather(upcoming: List<Weather>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background.copy(
                alpha = 0.24f
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(R.string.upcoming),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                upcoming.forEach {
                    SmallWeatherCard(weather = it)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun RowScope.SmallWeatherCard(weather: Weather) {
    Card(
        colors = CardDefaults.run {
            cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        },
        modifier = Modifier
            .height(128.dp)
            .weight(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = weather.tempC.tempToFormattedString())
            GlideImage(
                modifier = Modifier.size(48.dp),
                model = weather.conditionUrl,
                contentDescription = null
            )
            Text(text = weather.date.formattedShortDayOfWeek())
        }
    }
}

@Composable
private fun Initial() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.loading_data),
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun Error() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.error_loading_data),
            color = Color.Red,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}
