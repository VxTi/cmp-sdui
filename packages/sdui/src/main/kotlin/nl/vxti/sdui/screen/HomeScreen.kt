package nl.vxti.sdui.screen

import nl.vxti.common.components.ButtonComponent
import nl.vxti.common.components.ButtonVariant
import nl.vxti.common.components.SearchBarComponent
import nl.vxti.common.components.SpacerComponent
import nl.vxti.common.components.models.IComponent
import nl.vxti.common.events.NavigationEvent
import nl.vxti.core.AppRequestContext
import nl.vxti.sdui.screen.models.IScreen
import org.springframework.stereotype.Component

@Component
class HomeScreen : IScreen {
    override fun content(context: AppRequestContext): List<IComponent> {
        return listOf(
            SearchBarComponent(placeholder = "Search...", contentId = "search-2"),
            SpacerComponent(size = 3, contentId = "spacer-2"),
            ButtonComponent(
                "Hello world from SDUI",
                ButtonVariant.NORMAL,
                listOf(NavigationEvent("profile")),
                "test-button"
            ),
        )
    }

    override fun name(): String {
        return SCREEN_IDENTIFIER
    }

    companion object {
        const val SCREEN_IDENTIFIER: String = "home"
    }
}
