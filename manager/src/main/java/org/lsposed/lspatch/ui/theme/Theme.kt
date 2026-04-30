package org.lsposed.lspatch.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private fun createLightColorScheme(style: UIStyle): ColorScheme {
    return lightColorScheme(
        primary = style.primaryColor,
        onPrimary = Color.White,
        primaryContainer = style.primaryColor.copy(alpha = 0.12f),
        secondary = style.secondaryColor,
        onSecondary = Color.White,
        background = style.backgroundColor,
        onBackground = Color(0xFF1C1B1F),
        surface = style.surfaceColor,
        onSurface = Color(0xFF1C1B1F),
        surfaceVariant = style.surfaceColor,
        surfaceContainerLow = style.cardColor
    )
}

private fun createDarkColorScheme(style: UIStyle): ColorScheme {
    return darkColorScheme(
        primary = style.primaryColor,
        onPrimary = Color.White,
        primaryContainer = style.primaryColor.copy(alpha = 0.3f),
        secondary = style.secondaryColor,
        onSecondary = Color.White,
        background = style.backgroundColor,
        onBackground = Color(0xFFE6E1E5),
        surface = style.surfaceColor,
        onSurface = Color(0xFFE6E1E5),
        surfaceVariant = style.surfaceColor,
        surfaceContainerLow = style.cardColor
    )
}

@Composable
fun LSPTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    enableDynamicColor: Boolean = true,
    uiStyle: UIStyle = UIStyles.CLASSIC,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        uiStyle.useCustomColors -> {
            if (isDarkTheme) createDarkColorScheme(uiStyle)
            else createLightColorScheme(uiStyle)
        }
        enableDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDarkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.background.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = !isDarkTheme
        }
    }
    CompositionLocalProvider(LocalUIStyle provides uiStyle) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
