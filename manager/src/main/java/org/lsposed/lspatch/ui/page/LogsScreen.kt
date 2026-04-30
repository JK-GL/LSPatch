package org.lsposed.lspatch.ui.page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.annotation.Destination
import org.lsposed.lspatch.ui.component.CenterTopBar
import org.lsposed.lspatch.ui.theme.AppleBackground
import org.lsposed.lspatch.ui.theme.AppleText2

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun LogsScreen() {
    Scaffold(
        containerColor = AppleBackground,
        topBar = { CenterTopBar(stringResource(BottomBarDestination.Logs.label)) }
    ) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            text = "This page is not yet implemented",
            color = AppleText2,
            textAlign = TextAlign.Center
        )
    }
}