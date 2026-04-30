package org.lsposed.lspatch.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
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
    val fabStyle: FabStyle = FabStyle.EXTENDED
)

enum class TopBarStyle { CENTER, LEFT, FLOATING }
enum class HomeLayout { LIST, GRID, CARD }
enum class FabStyle { EXTENDED, CIRCLE, HIDDEN }

object UIStyles {
    val CLASSIC = UIStyle()

    val MINIMAL = UIStyle(
        cardShape = RoundedCornerShape(24.dp),
        cardElevation = 0.dp,
        innerPadding = 24.dp,
        itemSpacing = 20.dp,
        topBarStyle = TopBarStyle.LEFT,
        fabStyle = FabStyle.CIRCLE
    )

    val GLASS = UIStyle(
        cardShape = RoundedCornerShape(20.dp),
        cardElevation = 0.dp,
        innerPadding = 20.dp,
        itemSpacing = 14.dp,
        useGlassEffect = true,
        fabStyle = FabStyle.EXTENDED
    )

    val GRID = UIStyle(
        cardShape = RoundedCornerShape(12.dp),
        cardElevation = 4.dp,
        innerPadding = 12.dp,
        itemSpacing = 8.dp,
        homeLayout = HomeLayout.GRID,
        fabStyle = FabStyle.CIRCLE
    )

    val ALL = listOf(CLASSIC, MINIMAL, GLASS, GRID)
    val NAMES = listOf("经典", "极简", "毛玻璃", "网格")
}

val LocalUIStyle = staticCompositionLocalOf { UIStyles.CLASSIC }
