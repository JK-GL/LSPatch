package org.lsposed.lspatch.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val AppleDarkColors = darkColorScheme(
    primary = Color(0xFF7A8EFF),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF283066),
    onPrimaryContainer = Color(0xFFBBC3FF),
    secondary = Color(0xFF9E9E9E),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF3A3A3C),
    onSecondaryContainer = Color(0xFFE0E0E0),
    background = Color(0xFF000000),
    onBackground = Color(0xFFE5E5EA),
    surface = Color(0xFF1C1C1E),
    onSurface = Color(0xFFE5E5EA),
    surfaceVariant = Color(0xFF2C2C2E),
    onSurfaceVariant = Color(0xFFAEAEB2),
    surfaceContainerLow = Color(0xFF1C1C1E),
    surfaceContainer = Color(0xFF2C2C2E),
    surfaceContainerHigh = Color(0xFF3A3A3C),
    outline = Color(0xFF48484A),
    outlineVariant = Color(0xFF3A3A3C),
    error = Color(0xFFFF453A),
    onError = Color.White,
    inverseSurface = Color(0xFFE5E5EA),
    inverseOnSurface = Color(0xFF1C1C1E),
    inversePrimary = Color(0xFF3D5AFE),
    surfaceTint = Color(0xFF7A8EFF)
)

@Composable
fun LSPTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = AppleDarkColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.background.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
