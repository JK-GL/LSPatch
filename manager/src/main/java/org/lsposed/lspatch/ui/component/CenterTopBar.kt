package org.lsposed.lspatch.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                fontSize = 28.sp,
                letterSpacing = (-0.5).sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        modifier = Modifier.padding(start = 4.dp)
    )
}
