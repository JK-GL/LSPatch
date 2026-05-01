package org.lsposed.lspatch.ui.component.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import org.lsposed.lspatch.ui.theme.XMColors

@Composable
fun SettingsSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    title: String,
    desc: String? = null,
    extraContent: (@Composable ColumnScope.() -> Unit)? = null
) {
    SettingsSlot(modifier, enabled, icon, title, desc, extraContent) {
        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = XMColors.accent,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = XMColors.textSecondary.copy(alpha = 0.3f)
            )
        )
    }
}

@Preview
@Composable
private fun Preview() {
    var c by remember { mutableStateOf(false) }
    Column {
        SettingsSwitch(modifier = Modifier.clickable { c = !c }, checked = c, title = "Title", desc = "Desc")
    }
}
