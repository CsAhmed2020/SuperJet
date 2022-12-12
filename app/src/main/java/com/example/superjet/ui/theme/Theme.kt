package com.example.superjet.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val LightColorPalette = lightColorScheme(
    primary = Purple500,
    tertiary = Purple700,
    secondary = Teal200,
    surface = Teal200,
    onSurface = Color.White,
    onPrimary = Color.White,
    onTertiary = Color.White
)

@Composable
fun SuperJetTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colors =
        when {
            useDynamicColors && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
            useDynamicColors && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
            else -> LightColorPalette
        }


    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}