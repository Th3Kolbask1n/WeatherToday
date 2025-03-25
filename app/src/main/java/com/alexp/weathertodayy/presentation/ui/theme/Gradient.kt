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
            firstColor = Color(0xFF673AB7),
            secondColor = Color(0xFF9575CD),
            thirdColor = Color(0xFF512DA8),
            fourthColor = Color(0xFFD1C4E9),
        ),
        Gradient(
            firstColor = Color(0xFFE91E63),
            secondColor = Color(0xFFF06292),
            thirdColor = Color(0xFFC2185B),
            fourthColor = Color(0xFFF8BBD0),
        ),
        Gradient(
            firstColor = Color(0xFF009688),
            secondColor = Color(0xFF4DB6AC),
            thirdColor = Color(0xFF00796B),
            fourthColor = Color(0xFFB2DFDB),
        ),
        Gradient(
            firstColor = Color(0xFFFF9800),
            secondColor = Color(0xFFFFB74D),
            thirdColor = Color(0xFFF57C00),
            fourthColor = Color(0xFFFFE0B2),
        ),
        Gradient(
            firstColor = Color(0xFF3F51B5),
            secondColor = Color(0xFF7986CB),
            thirdColor = Color(0xFF303F9F),
            fourthColor = Color(0xFFC5CAE9),
        )
    )
}
