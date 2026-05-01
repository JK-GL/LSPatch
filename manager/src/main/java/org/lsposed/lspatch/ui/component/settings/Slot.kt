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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.lsposed.lspatch.ui.theme.*

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
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.5f),
        shape = RoundedCornerShape(AppleDesign.CornerM),
        color = AppleSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppleDesign.CardPadding, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AppleAccent,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                Spacer(Modifier.width(2.dp))
            }

            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = AppleText,
                    fontSize = AppleDesign.BodySize.sp,
                    fontWeight = FontWeight.Medium
                )
                if (!desc.isNullOrBlank()) {
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = desc,
                        color = AppleText2,
                        fontSize = AppleDesign.SubSize.sp
                    )
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
