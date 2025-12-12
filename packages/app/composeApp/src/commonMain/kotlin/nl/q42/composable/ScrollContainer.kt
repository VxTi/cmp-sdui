package me.vxti.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.vxti.ViewController
import me.vxti.common.ScrollableContainer

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