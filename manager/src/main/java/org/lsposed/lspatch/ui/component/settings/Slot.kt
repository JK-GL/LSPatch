package org.lsposed.lspatch.ui.component.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.lsposed.lspatch.ui.theme.XMColors
import org.lsposed.lspatch.ui.theme.XMDimensions
import org.lsposed.lspatch.ui.theme.XMTypography

@Composable
fun SettingsSlot(
    modifier: Modifier,
    enabled: Boolean,
    icon: ImageVector? = null,
    title: String,
    desc: String?,
    extraContent: (@Composable ColumnScope.() -> Unit)? = null,
    action: (@Composable RowScope.() -> Unit)?,
) {
    Surface(
        modifier = modifier.fillMaxWidth().alpha(if (enabled) 1f else 0.5f),
        shape = RoundedCornerShape(XMDimensions.cornerM),
        color = XMColors.glassSurface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = XMDimensions.cardPadding, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(XMDimensions.spaceM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, tint = XMColors.accent, modifier = Modifier.size(XMDimensions.iconM))
            } else { Spacer(Modifier.width(2.dp)) }
            Column(Modifier.weight(1f)) {
                Text(text = title, style = XMTypography.subheadline, color = XMColors.textPrimary)
                if (!desc.isNullOrBlank()) {
                    Spacer(Modifier.height(3.dp))
                    Text(text = desc, style = XMTypography.caption, color = XMColors.textSecondary)
                }
                extraContent?.invoke(this)
            }
            action?.invoke(this)
        }
    }
}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    title: String,
    desc: String? = null,
    extraContent: (@Composable ColumnScope.() -> Unit)? = null
) = SettingsSlot(modifier, enabled, icon, title, desc, extraContent, null)
