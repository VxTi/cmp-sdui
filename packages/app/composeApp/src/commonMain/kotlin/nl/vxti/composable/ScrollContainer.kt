package nl.vxti.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.vxti.ViewController
import nl.vxti.common.ScrollableContainer

@Composable
internal fun ScrollContainerDrawable(component: ScrollableContainer, controller: ViewController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            DynamicContentList(component.content, controller)
        }
    }
}