package org.lsposed.lspatch.ui.page.manage

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import org.lsposed.lspatch.R
import org.lsposed.lspatch.ui.component.AnywhereDropdown
import org.lsposed.lspatch.ui.component.AppItem
import org.lsposed.lspatch.ui.theme.AppleAccent
import org.lsposed.lspatch.ui.theme.AppleDesign
import org.lsposed.lspatch.ui.theme.AppleText
import org.lsposed.lspatch.ui.theme.AppleText2
import org.lsposed.lspatch.ui.viewmodel.manage.ModuleManageViewModel
import org.lsposed.lspatch.util.LSPPackageManager

@Composable
fun ModuleManageBody() {
    val context = LocalContext.current
    val viewModel = viewModel<ModuleManageViewModel>()

    if (viewModel.appList.isEmpty()) {
        Box(Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = if (LSPPackageManager.appList.isEmpty()) {
                    stringResource(R.string.manage_loading)
                } else {
                    stringResource(R.string.manage_no_modules)
                },
                color = AppleText2,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            contentPadding = PaddingValues(horizontal = AppleDesign.PagePadding, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(AppleDesign.ItemSpacing)
        ) {
            items(
                items = viewModel.appList,
                key = { it.first.app.packageName }
            ) { item ->
                var expanded by remember { mutableStateOf(false) }
                val settingsIntent = remember { LSPPackageManager.getSettingsIntent(item.first.app.packageName) }
                AnywhereDropdown(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    onClick = { settingsIntent?.let { context.startActivity(it) } },
                    onLongClick = { expanded = true },
                    surface = {
                        AppItem(
                            icon = LSPPackageManager.getIcon(item.first),
                            label = item.first.label,
                            packageName = item.first.app.packageName,
                            additionalContent = {
                                Text(
                                    text = item.second.description,
                                    color = AppleText2,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        append(AnnotatedString("API", SpanStyle(color = AppleAccent)))
                                        append("  ")
                                        append(item.second.api.toString())
                                    },
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppleText,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        )
                    }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = item.first.label, color = AppleAccent) },
                        onClick = {},
                        enabled = false
                    )
                    if (settingsIntent != null) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.manage_module_settings)) },
                            onClick = { context.startActivity(settingsIntent) }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.manage_app_info)) },
                        onClick = {
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", item.first.app.packageName, null)
                            )
                            context.startActivity(intent)
                        }
                    )
                }
            }
            item { Spacer(Modifier.height(AppleDesign.NavBarBottomMargin + 16.dp)) }
        }
    }
}
