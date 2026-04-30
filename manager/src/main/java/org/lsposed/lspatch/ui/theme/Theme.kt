package org.lsposed.lspatch.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

// iOS风格暗色配色
val AppleBackground = Color(0xFF000000)
val AppleSurface = Color(0xFF1C1C1E)
val AppleSurface2 = Color(0xFF2C2C2E)
val AppleSurface3 = Color(0xFF3A3A3C)
val AppleText = Color(0xFFE5E5EA)
val AppleText2 = Color(0xFF8E8E93)
val AppleAccent = Color(0xFF7A8EFF)
val AppleSeparator = Color(0xFF38383A)
val AppleRed = Color(0xFFFF453A)
val AppleGreen = Color(0xFF30D158)

private val AppleDarkColors = darkColorScheme(
    primary = AppleAccent,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF283066),
    onPrimaryContainer = Color(0xFFBBC3FF),
    secondary = AppleText2,
    onSecondary = Color.White,
    secondaryContainer = AppleSurface3,
    onSecondaryContainer = AppleText,
    background = AppleBackground,
    onBackground = AppleText,
    surface = AppleSurface,
    onSurface = AppleText,
    surfaceVariant = AppleSurface2,
    onSurfaceVariant = AppleText2,
    surfaceContainerLow = AppleSurface,
    surfaceContainer = AppleSurface2,
    surfaceContainerHigh = AppleSurface3,
    outline = AppleSeparator,
    outlineVariant = AppleSurface3,
    error = AppleRed,
    onError = Color.White,
    inverseSurface = AppleText,
    inverseOnSurface = AppleBackground,
    inversePrimary = Color(0xFF3D5AFE),
    surfaceTint = AppleAccent
)

@Composable
fun LSPTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = Color.Black.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(
        colorScheme = AppleDarkColors,
        typography = Typography,
        content = content
    )
}
