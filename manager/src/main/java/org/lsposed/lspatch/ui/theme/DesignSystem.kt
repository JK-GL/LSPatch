package org.lsposed.lspatch.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background

object XMColors {
    val bgGradientStart = Color(0xFF0D1117)
    val bgGradientMid = Color(0xFF161B22)
    val bgGradientEnd = Color(0xFF0D1117)
    val glassSurface = Color(0x30FFFFFF)
    val glassBorder = Color(0x15FFFFFF)
    val textPrimary = Color(0xFFE5E5EA)
    val textSecondary = Color(0xFF8E8E93)
    val accent = Color(0xFF64D2FF)
    val success = Color(0xFF30D158)
    val error = Color(0xFFFF453A)
    val divider = Color(0x15FFFFFF)
}

object XMDimensions {
    val cornerS = 12.dp
    val cornerM = 16.dp
    val cornerL = 24.dp
    val spaceXS = 4.dp
    val spaceS = 8.dp
    val spaceM = 12.dp
    val spaceL = 16.dp
    val pagePadding = 20.dp
    val cardPadding = 16.dp
    val navBarHeight = 56.dp
    val navBarBottomMargin = 76.dp
    val iconS = 18.dp
    val iconM = 22.dp
    val iconL = 28.dp
    val avatarSize = 44.dp
    val dividerThickness = 0.5.dp
}

object XMTypography {
    val title = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp)
    val headline = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    val subheadline = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
    val body = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)
    val caption = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal)
    val tiny = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium)
}

@Composable
fun XMBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(XMColors.bgGradientStart, XMColors.bgGradientMid, XMColors.bgGradientEnd))
        )
    ) { content() }
}

@Composable
fun XMCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(XMDimensions.cornerM),
        color = XMColors.glassSurface,
        border = BorderStroke(XMDimensions.dividerThickness, XMColors.glassBorder)
    ) {
        Column(Modifier.padding(XMDimensions.cardPadding), content = content)
    }
}
