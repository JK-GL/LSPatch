package org.lsposed.lspatch.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.lsposed.lspatch.ui.theme.LocalUIStyle
import org.lsposed.lspatch.ui.theme.TopBarStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopBar(text: String) {
    val style = LocalUIStyle.current
    val titleContent: @Composable () -> Unit = {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            style = MaterialTheme.typography.titleMedium
        )
    }
    when (style.topBarStyle) {
        TopBarStyle.CENTER -> CenterAlignedTopAppBar(title = titleContent)
        TopBarStyle.LEFT -> TopAppBar(title = titleContent)
        TopBarStyle.FLOATING -> CenterAlignedTopAppBar(
            title = titleContent,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        )
    }
}
