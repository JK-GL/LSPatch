package org.lsposed.lspatch.ui.theme

import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat

val AppleBackground = XMColors.bgGradientMid
val AppleSurface = XMColors.glassSurface
val AppleSurface2 = XMColors.glassSurface
val AppleSurface3 = Color(0x40FFFFFF)
val AppleText = XMColors.textPrimary
val AppleText2 = XMColors.textSecondary
val AppleAccent = XMColors.accent
val AppleSeparator = XMColors.divider
val AppleRed = XMColors.error
val AppleGreen = XMColors.success

object AppleDesign {
    val CornerS = 12.dp
    val CornerM = 16.dp
    val CornerL = 24.dp
    const val TitleSize = 28
    const val HeadlineSize = 20
    const val BodySize = 16
    const val SubSize = 14
    const val CaptionSize = 12
    const val TinySize = 10
    val PagePadding = 16.dp
    val CardPadding = 16.dp
    val ItemSpacing = 12.dp
    val NavBarHeight = 56.dp
    val NavBarBottomMargin = 72.dp
}

private val AppleDarkColors = darkColorScheme(
    primary = AppleAccent,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF283066),
    onPrimaryContainer = Color(0xFFBBC3FF),
    secondary = AppleText2,
    onSecondary = Color.White,
    secondaryContainer = AppleSurface3,
    onSecondaryContainer = AppleText,
    background = XMColors.bgGradientMid,
    onBackground = AppleText,
    surface = XMColors.glassSurface,
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

private val AppleTypography = Typography(
    displayLarge = TextStyle(fontSize = 34.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp),
    displayMedium = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp),
    displaySmall = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
    headlineLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
    headlineMedium = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
    titleLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
    titleSmall = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    bodyLarge = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    bodyMedium = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal),
    bodySmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal),
    labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    labelMedium = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium),
    labelSmall = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium)
)

@Composable
fun LSPTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = XMColors.bgGradientStart.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(colorScheme = AppleDarkColors, typography = AppleTypography, content = content)
}