package org.lsposed.lspatch.ui.page

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.lsposed.lspatch.R
import org.lsposed.lspatch.share.LSPConfig
import org.lsposed.lspatch.ui.component.CenterTopBar
import org.lsposed.lspatch.ui.page.destinations.ManageScreenDestination
import org.lsposed.lspatch.ui.page.destinations.NewPatchScreenDestination
import org.lsposed.lspatch.ui.util.HtmlText
import org.lsposed.lspatch.ui.theme.AppleAccent
import org.lsposed.lspatch.ui.theme.AppleBackground
import org.lsposed.lspatch.ui.theme.AppleSurface
import org.lsposed.lspatch.ui.theme.AppleSurface2
import org.lsposed.lspatch.ui.theme.AppleSurface3
import org.lsposed.lspatch.ui.theme.AppleText
import org.lsposed.lspatch.ui.theme.AppleText2
import org.lsposed.lspatch.ui.theme.AppleGreen
import org.lsposed.lspatch.ui.theme.AppleRed
import org.lsposed.lspatch.ui.theme.AppleDesign
import org.lsposed.lspatch.ui.theme.AppleSeparator
import org.lsposed.lspatch.ui.util.LocalSnackbarHost
import org.lsposed.lspatch.util.ShizukuApi
import rikka.shizuku.Shizuku

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    var isIntentLaunched by rememberSaveable { mutableStateOf(false) }
    val activity = LocalContext.current as Activity
    val intent = activity.intent
    LaunchedEffect(Unit) {
        if (!isIntentLaunched && intent.action == Intent.ACTION_VIEW && intent.hasCategory(Intent.CATEGORY_DEFAULT) && intent.type == "application/vnd.android.package-archive") {
            isIntentLaunched = true
            val uri = intent.data
            if (uri != null) {
                navigator.navigate(ManageScreenDestination)
                navigator.navigate(NewPatchScreenDestination(id = ACTION_INTENT_INSTALL, data = uri))
            }
        }
    }
    Scaffold(
        containerColor = AppleBackground,
        topBar = { CenterTopBar(stringResource(R.string.app_name)) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = AppleDesign.PagePadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(4.dp))
            ShizukuCard()
            InfoCard()
            SupportCard()
            Spacer(Modifier.height(80.dp))
        }
    }
}

private val listener: (Int, Int) -> Unit = { _, grantResult ->
    ShizukuApi.isPermissionGranted = grantResult == PackageManager.PERMISSION_GRANTED
}

@Composable
private fun ShizukuCard() {
    LaunchedEffect(Unit) { Shizuku.addRequestPermissionResultListener(listener) }
    DisposableEffect(Unit) { onDispose { Shizuku.removeRequestPermissionResultListener(listener) } }
    
    val granted = ShizukuApi.isPermissionGranted
    val bgColor = if (granted) AppleGreen.copy(alpha = 0.12f) else AppleRed.copy(alpha = 0.12f)
    val iconColor = if (granted) AppleGreen else AppleRed
    
    Surface(
        shape = RoundedCornerShape(AppleDesign.CornerM),
        color = bgColor,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (ShizukuApi.isBinderAvailable && !ShizukuApi.isPermissionGranted) {
                    Shizuku.requestPermission(114514)
                }
            }
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (granted) Icons.Outlined.CheckCircle else Icons.Outlined.Warning,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = stringResource(if (granted) R.string.shizuku_available else R.string.shizuku_unavailable),
                    color = AppleText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = AppleDesign.BodySize.sp
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = if (granted) "API " + Shizuku.getVersion() else stringResource(R.string.home_shizuku_warning),
                    color = AppleText2,
                    fontSize = AppleDesign.SubSize.sp
                )
            }
        }
    }
}

private val apiVersion = if (Build.VERSION.PREVIEW_SDK_INT != 0) {
    "${Build.VERSION.CODENAME} Preview (API ${Build.VERSION.PREVIEW_SDK_INT})"
} else {
    "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
}
private val device = buildString {
    append(Build.MANUFACTURER[0].uppercaseChar().toString() + Build.MANUFACTURER.substring(1))
    if (Build.BRAND != Build.MANUFACTURER) append(" " + Build.BRAND[0].uppercaseChar() + Build.BRAND.substring(1))
    append(" " + Build.MODEL)
}

@Composable
private fun InfoCard() {
    val context = LocalContext.current
    val snackbarHost = LocalSnackbarHost.current
    val scope = rememberCoroutineScope()
    Surface(
        shape = RoundedCornerShape(AppleDesign.CornerM),
        color = AppleSurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            val contents = StringBuilder()
            @Composable
            fun InfoRow(label: String, value: String) {
                contents.appendLine(label).appendLine(value).appendLine()
                Text(text = label, color = AppleText2, fontSize = AppleDesign.CaptionSize.sp)
                Text(text = value, color = AppleText, fontSize = AppleDesign.BodySize.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(16.dp))
            }
            InfoRow(stringResource(R.string.home_api_version), "${LSPConfig.instance.API_CODE}")
            InfoRow(stringResource(R.string.home_lspatch_version), LSPConfig.instance.VERSION_NAME + " (${LSPConfig.instance.VERSION_CODE})")
            InfoRow(stringResource(R.string.home_framework_version), LSPConfig.instance.CORE_VERSION_NAME + " (${LSPConfig.instance.CORE_VERSION_CODE})")
            InfoRow(stringResource(R.string.home_system_version), apiVersion)
            InfoRow(stringResource(R.string.home_device), device)
            InfoRow(stringResource(R.string.home_system_abi), Build.SUPPORTED_ABIS[0])
            
            val copiedMessage = stringResource(R.string.home_info_copied)
            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("LSPatch", contents.toString()))
                    scope.launch { snackbarHost.showSnackbar(copiedMessage) }
                }
            ) {
                Text(
                    stringResource(android.R.string.copy),
                    color = AppleAccent,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SupportCard() {
    Surface(
        shape = RoundedCornerShape(AppleDesign.CornerM),
        color = AppleSurface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(R.string.home_support),
                color = AppleText,
                fontWeight = FontWeight.SemiBold,
                fontSize = AppleDesign.HeadlineSize.sp
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.home_description),
                color = AppleText2,
                fontSize = AppleDesign.SubSize.sp,
                lineHeight = 20.sp
            )
            HtmlText(
                stringResource(
                    R.string.home_view_source_code,
                    "<b><a href=\"https://github.com/JingMatrix/LSPatch\">GitHub</a></b>",
                    "<b><a href=\"https://t.me/LSPosed\">Telegram</a></b>"
                )
            )
        }
    }
}
