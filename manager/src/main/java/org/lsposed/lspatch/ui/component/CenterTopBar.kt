package org.lsposed.lspatch.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.lsposed.lspatch.ui.theme.AppleDesign
import org.lsposed.lspatch.ui.theme.AppleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopBar(text: String) {
    TopAppBar(
        title = {
            Text(
                text = text,
                color = AppleText,
                fontWeight = FontWeight.Bold,
                fontSize = AppleDesign.TitleSize.sp,
                letterSpacing = (-0.5).sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = AppleText
        ),
        modifier = Modifier.padding(horizontal = AppleDesign.PagePadding)
    )
}
