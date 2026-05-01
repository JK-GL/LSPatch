package org.lsposed.lspatch.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.lsposed.lspatch.ui.theme.AppleDesign
import org.lsposed.lspatch.ui.theme.XMColors
import org.lsposed.lspatch.ui.theme.XMTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopBar(text: String) {
    TopAppBar(
        title = {
            Text(
                text = text,
                style = XMTypography.title
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = XMColors.bgGradientMid,
            titleContentColor = XMColors.textPrimary
        ),
        modifier = Modifier.padding(horizontal = AppleDesign.PagePadding)
    )
}
