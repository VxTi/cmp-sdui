package nl.vxti.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.vxti.common.components.SpacerComponent

@Composable
internal fun SpacerDrawable(component: SpacerComponent) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height((4 * component.size).dp)
    )
}