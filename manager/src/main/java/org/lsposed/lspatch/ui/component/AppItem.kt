package org.lsposed.lspatch.ui.component

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import org.lsposed.lspatch.ui.theme.LSPTheme
import org.lsposed.lspatch.ui.theme.XMColors
import org.lsposed.lspatch.ui.theme.XMDimensions
import org.lsposed.lspatch.ui.theme.XMTypography

@Composable
fun AppItem(
    modifier: Modifier = Modifier,
    icon: ImageBitmap,
    label: String,
    packageName: String,
    checked: Boolean? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    additionalContent: (@Composable ColumnScope.() -> Unit)? = null,
) {
    if (checked != null && rightIcon != null) {
        throw IllegalArgumentException()
    }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(XMDimensions.cornerM),
        color = XMColors.glassSurface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(XMDimensions.cardPadding),
            horizontalArrangement = Arrangement.spacedBy(XMDimensions.spaceM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(bitmap = icon, contentDescription = label, tint = Color.Unspecified, modifier = Modifier.size(XMDimensions.avatarSize))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(label, style = XMTypography.subheadline, color = XMColors.textPrimary)
                Text(text = packageName, color = XMColors.textSecondary, fontFamily = FontFamily.Monospace, style = XMTypography.caption)
                additionalContent?.invoke(this)
            }
            if (checked != null) {
                Checkbox(checked = checked, onCheckedChange = null,
                    colors = CheckboxDefaults.colors(checkedColor = XMColors.accent, uncheckedColor = XMColors.textSecondary, checkmarkColor = Color.White))
            }
            if (rightIcon != null) { rightIcon() }
            else if (checked == null) {
                Icon(Icons.Filled.ArrowForwardIos, null, tint = XMColors.textSecondary.copy(alpha = 0.65f), modifier = Modifier.size(14.dp))
            }
        }
    }
}

@Preview
@Composable
private fun AppItemPreview() {
    LSPTheme {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(androidx.compose.material3.MaterialTheme.colorScheme.primary.toArgb())
        AppItem(icon = shape.toBitmap().asImageBitmap(), label = "Sample", packageName = "org.lsposed.sample")
    }
}
