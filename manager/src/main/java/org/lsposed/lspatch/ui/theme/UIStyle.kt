package org.lsposed.lspatch.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class UIStyle(
    val cardShape: Shape = RoundedCornerShape(16.dp),
    val cardElevation: Dp = 2.dp,
    val innerPadding: Dp = 16.dp,
    val itemSpacing: Dp = 12.dp,
    val topBarStyle: TopBarStyle = TopBarStyle.CENTER,
    val homeLayout: HomeLayout = HomeLayout.LIST,
    val useGlassEffect: Boolean = false,
    val fabStyle: FabStyle = FabStyle.EXTENDED,
    // 配色方案
    val primaryColor: Color = Color(0xFF6750A4),
    val secondaryColor: Color = Color(0xFF625B71),
    val backgroundColor: Color = Color(0xFFFFFBFE),
    val surfaceColor: Color = Color(0xFFFFFBFE),
    val cardColor: Color = Color(0xFFFFFFFF),
    val cardAlpha: Float = 1.0f,
    val useCustomColors: Boolean = false
)

enum class TopBarStyle { CENTER, LEFT, FLOATING }
enum class HomeLayout { LIST, GRID, CARD }
enum class FabStyle { EXTENDED, CIRCLE, HIDDEN }

object UIStyles {
    // 经典：标准Material 3
    val CLASSIC = UIStyle()
    
    // 极简：大圆角、无阴影、清新配色
    val MINIMAL = UIStyle(
        cardShape = RoundedCornerShape(24.dp),
        cardElevation = 0.dp,
        innerPadding = 24.dp,
        itemSpacing = 20.dp,
        topBarStyle = TopBarStyle.LEFT,
        fabStyle = FabStyle.CIRCLE,
        primaryColor = Color(0xFF1A1A1A),
        secondaryColor = Color(0xFF666666),
        backgroundColor = Color(0xFFF8F9FA),
        surfaceColor = Color(0xFFF0F2F5),
        cardColor = Color(0xFFFFFFFF),
        useCustomColors = true
    )
    
    // 毛玻璃：半透明、深蓝紫色调、通透感
    val GLASS = UIStyle(
        cardShape = RoundedCornerShape(20.dp),
        cardElevation = 0.dp,
        innerPadding = 20.dp,
        itemSpacing = 14.dp,
        useGlassEffect = true,
        fabStyle = FabStyle.EXTENDED,
        primaryColor = Color(0xFF7C4DFF),
        secondaryColor = Color(0xFFB388FF),
        backgroundColor = Color(0xFF0D1117),
        surfaceColor = Color(0xFF161B22),
        cardColor = Color(0x40FFFFFF),
        cardAlpha = 0.25f,
        useCustomColors = true
    )
    
    // 网格：暗黑高对比
    val GRID = UIStyle(
        cardShape = RoundedCornerShape(12.dp),
        cardElevation = 4.dp,
        innerPadding = 12.dp,
        itemSpacing = 8.dp,
        homeLayout = HomeLayout.GRID,
        fabStyle = FabStyle.CIRCLE,
        primaryColor = Color(0xFF00E676),
        secondaryColor = Color(0xFF69F0AE),
        backgroundColor = Color(0xFF121212),
        surfaceColor = Color(0xFF1E1E1E),
        cardColor = Color(0xFF2D2D2D),
        useCustomColors = true
    )
    
    val ALL = listOf(CLASSIC, MINIMAL, GLASS, GRID)
    val NAMES = listOf("经典", "极简", "毛玻璃", "暗黑网格")
}

val LocalUIStyle = staticCompositionLocalOf { UIStyles.CLASSIC }
