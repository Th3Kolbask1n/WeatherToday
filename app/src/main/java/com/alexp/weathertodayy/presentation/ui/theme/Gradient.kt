package com.alexp.weathertodayy.presentation.ui.theme


import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


data class Gradient(
    val primaryGradient: Brush,
    val secondaryGradient: Brush,
    val shadowColor: Color
) {
    constructor(
        firstColor: Color,
        secondColor: Color,
        thirdColor: Color,
        fourthColor: Color,
    ) : this(
        primaryGradient = Brush.linearGradient(listOf(firstColor, secondColor)),
        secondaryGradient = Brush.linearGradient(listOf(thirdColor, fourthColor)),
        shadowColor = firstColor
    )
}

object CardGradients {
    val gradients = listOf(
        Gradient(
            firstColor = Color(0xFF2196F3),
            secondColor = Color(0xFF64B5F6),
            thirdColor = Color(0xFF1976D2),
            fourthColor = Color(0xFFBBDEFB),
        ),
        Gradient(
            firstColor = Color(0xFFFF5722),
            secondColor = Color(0xFFFF8A65),
            thirdColor = Color(0xFFD84315),
            fourthColor = Color(0xFFFFCCBC),
        ),
        Gradient(
            firstColor = Color(0xFF4CAF50),
            secondColor = Color(0xFF81C784),
            thirdColor = Color(0xFF2E7D32),
            fourthColor = Color(0xFFC8E6C9),
        ),
        Gradient(
            firstColor = Color(0xFFFFC107),
            secondColor = Color(0xFFFFD54F),
            thirdColor = Color(0xFFFFA000),
            fourthColor = Color(0xFFFFF8E1),
        ),
        Gradient(
            firstColor = Color(0xFF9C27B0),
            secondColor = Color(0xFFCE93D8),
            thirdColor = Color(0xFF6A1B9A),
            fourthColor = Color(0xFFE1BEE7),
        )
    )
}