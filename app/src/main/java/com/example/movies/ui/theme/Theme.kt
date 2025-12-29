package com.example.movies.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    surface = blue,

    onPrimary = Color.Black,
    onSurface = Color.White
)

@Composable
fun MoviesTheme(
    isEnableEdgeToEdge: Boolean,
    content: @Composable () -> Unit
) {
    if (isEnableEdgeToEdge) {
        SetupEdgeToEdge()
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
private fun SetupEdgeToEdge() {
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowCompat.getInsetsController(window, window.decorView)

        controller.isAppearanceLightStatusBars = false
        controller.isAppearanceLightNavigationBars = true
    }
}