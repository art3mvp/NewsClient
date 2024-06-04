package com.art3mvp.newsclient.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkGrey300,
    secondary = DarkGrey300,
    onPrimary = Color.White,
    onSecondary = DarkGrey100,
    surfaceVariant = DarkGrey300,
    surface = DarkGrey300,
    onPrimaryContainer = Color.White,
    onSurface = DarkGrey100


)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color.White,
    onPrimary = DarkGrey300,
    onSecondary = DarkGrey100,
    surfaceVariant = Color.White,
    surface = Color.White,
    onPrimaryContainer = DarkGrey300,
    onSurface = DarkGrey100


)

@Composable
fun NewsClientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}