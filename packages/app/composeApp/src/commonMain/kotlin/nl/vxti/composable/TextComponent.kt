package nl.vxti.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.vxti.ViewController
import nl.vxti.common.TextComponent
import nl.vxti.common.TextFormatting
import nl.vxti.common.TextSize

@Composable
internal fun TextComponentDrawable(component: TextComponent, controller: ViewController) {
    Text(
        text = component.text,
        style = getTextStyle(component),
        fontWeight = getFontWeight(component),
        modifier = Modifier.padding(bottom = 8.dp),
        fontSize = getTextSize(component).sp
    )
}

private fun getFontWeight(component: TextComponent): FontWeight {
    return when (component.formatting) {
        TextFormatting.BOLD -> FontWeight.Bold
        else -> FontWeight.Normal
    }
}

private fun getTextSize(component: TextComponent): Int {
    return when (component.size) {
        TextSize.EXTRA_SMALL -> 8
        TextSize.SMALL -> 12
        TextSize.NORMAL -> 14
        TextSize.LARGE -> 16
        TextSize.EXTRA_LARGE -> 20
    }
}

@Composable
private fun getTextStyle(component: TextComponent): TextStyle {
    return when (component.formatting) {
        TextFormatting.BOLD -> MaterialTheme.typography.titleLarge
        TextFormatting.ITALIC -> MaterialTheme.typography.titleMedium
        TextFormatting.UNDERLINE -> MaterialTheme.typography.titleSmall
        TextFormatting.NORMAL -> MaterialTheme.typography.titleLarge
    }
}
