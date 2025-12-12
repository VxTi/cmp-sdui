package nl.vxti.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import nl.vxti.common.components.ButtonComponent
import nl.vxti.common.components.ImageComponent
import nl.vxti.common.components.SearchBarComponent
import nl.vxti.common.components.SpacerComponent
import nl.vxti.common.components.TextComponent
import nl.vxti.common.components.container.ListItemContainer
import nl.vxti.common.components.container.CarouselContainerComponent
import nl.vxti.common.components.models.IComponent

@Composable
internal fun DynamicContentList(components: List<IComponent>, controller: NavHostController) {
    Column {
        reduceDuplicateComponents(components)
            .forEach { element -> DrawableComponentSequence(element, controller) }
    }
}

// Removes components with duplicate contentId, keeping only the first occurrence
fun reduceDuplicateComponents(components: List<IComponent>): List<IComponent> {
    return components.groupBy { it.contentId }
        .filter { it.value.size == 1 }
        .flatMap { it.value }
}


@Composable
internal fun DrawableComponentSequence(component: IComponent, controller: NavHostController) {
    when (component) {
        is TextComponent -> TextComponentDrawable(component)
        is SpacerComponent -> SpacerDrawable(component)
        is SearchBarComponent -> SearchBarDrawable(component)
        is ButtonComponent -> ButtonComposable(component)
        is ImageComponent -> ImageComponentDrawable(component)
        is CarouselContainerComponent -> CarouselContainerDrawable(component, controller)
        is ListItemContainer -> ListItemContainerDrawable(component)
    }
}