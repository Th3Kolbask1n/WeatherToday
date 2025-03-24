package com.alexp.weathertodayy.presentation.favourite


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alexp.weathertodayy.presentation.ui.theme.CardGradients
import com.alexp.weathertodayy.presentation.ui.theme.Gradient


@Composable
fun FavouriteContent(
    component: FavorutieComponent
) {
    val state by component.model.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
    itemsIndexed(
            items = state.cityItems,
            key = { _, item -> item.city.id }
        ) { index, item ->
            CityCard(
                cityItem = item,
                index = index
            )
        }
    }
}

@Composable
private fun CityCard(
    cityItem: FavouriteStore.State.CityItem,
    index: Int
) {
    val gradient = getGradientByIndex(index)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                spotColor = gradient.shadowColor,
                shape = RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = gradient.shadowColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(gradient.primaryGradient)
                .fillMaxSize()
                .sizeIn(minHeight = 180.dp)
                .drawBehind {
                    drawCircle(
                        brush = gradient.secondaryGradient,
                        center = Offset(
                            x = center.x + size.width / 4,
                            y = center.y - size.height / 3
                        ),
                        radius = size.maxDimension / 3
                    )
                }
                .padding(20.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = cityItem.city.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}

private fun getGradientByIndex(index: Int): Gradient {
    val gradients = CardGradients.gradients
    return gradients[index % gradients.size]
}