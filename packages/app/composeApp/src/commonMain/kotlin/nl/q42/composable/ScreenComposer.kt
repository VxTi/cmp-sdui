package me.vxti.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.vxti.ViewController
import me.vxti.common.*
import me.vxti.common.screen.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ServerDrivenScreen(screen: Screen?, controller: ViewController) {
    if (screen == null) {
        return
    }

    val isRefreshing by controller.screenStateBusy.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { controller.refreshScreen() }
    ) {
        Column {
            DynamicContentList(screen.content, controller)
        }
    }
}

@Composable
internal fun DynamicContentList(components: List<ServerComponent>, controller: ViewController) {
    Column {
        reduceDuplicateComponents(components)
            .forEach { element -> DrawableComponentSequence(element, controller) }
    }
}

// Removes components with duplicate contentId, keeping only the first occurrence
fun reduceDuplicateComponents(components: List<ServerComponent>): List<ServerComponent> {
    return components.groupBy { it.contentId }
        .filter { it.value.size == 1 }
        .flatMap { it.value }
}


@Composable
internal fun DrawableComponentSequence(component: ServerComponent, controller: ViewController) {
    when (component) {
        is TextComponent -> TextComponentDrawable(component, controller)
        is SpacerComponent -> SpacerDrawable(component, controller)
        is SearchBarComponent -> SearchBarDrawable(component, controller)
        is ButtonComponent -> ButtonDrawable(component, controller)
        is ImageComponent -> ImageComponentDrawable(component, controller)
        is ScrollableContainer -> ScrollContainerDrawable(component, controller)
        is ListItemContainer -> ListItemContainerDrawable(component, controller)
    }
}