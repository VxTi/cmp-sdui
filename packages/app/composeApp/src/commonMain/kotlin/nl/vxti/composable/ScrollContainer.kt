package nl.vxti.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import nl.vxti.common.components.container.CarouselContainerComponent

@Composable
internal fun CarouselContainerDrawable(component: CarouselContainerComponent, controller: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            DynamicContentList(component.content, controller)
        }
    }
}