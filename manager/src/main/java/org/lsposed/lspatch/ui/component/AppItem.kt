package org.lsposed.lspatch.ui.component

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import org.lsposed.lspatch.ui.theme.*

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
        throw IllegalArgumentException("`checked` and `rightIcon` should not be both set")
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppleDesign.CornerM),
        color = AppleSurface,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppleDesign.CardPadding, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                bitmap = icon,
                contentDescription = label,
                tint = Color.Unspecified,
                modifier = Modifier.size(44.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = label,
                    color = AppleText,
                    fontSize = AppleDesign.BodySize.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = packageName,
                    color = AppleText2,
                    fontFamily = FontFamily.Monospace,
                    fontSize = AppleDesign.CaptionSize.sp
                )
                additionalContent?.invoke(this)
            }

            if (checked != null) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(
                        checkedColor = AppleAccent,
                        uncheckedColor = AppleText2,
                        checkmarkColor = Color.White
                    )
                )
            }

            if (rightIcon != null) {
                rightIcon()
            } else if (checked == null) {
                Icon(
                    imageVector = Icons.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = AppleText2.copy(alpha = 0.65f),
                    modifier = Modifier.size(14.dp)
                )
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
        shape.setColor(MaterialTheme.colorScheme.primary.toArgb())
        Box(Modifier.background(AppleBackground).padding(16.dp)) {
            AppItem(
                icon = shape.toBitmap().asImageBitmap(),
                label = "Sample App",
                packageName = "org.lsposed.sample",
                rightIcon = { Icon(Icons.Filled.ArrowForwardIos, null, tint = AppleText2) }
            )
        }
    }
}
