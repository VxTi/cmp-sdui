package nl.vxti.sdui.screen

import nl.vxti.common.components.TextComponent
import nl.vxti.common.components.models.IComponent
import nl.vxti.core.AppRequestContext
import nl.vxti.sdui.screen.models.IScreen

@org.springframework.stereotype.Component
class SettingsScreen : IScreen {

    override fun content(context: AppRequestContext): List<IComponent> {
        return listOf(
            TextComponent(text = "Settings", contentId = "settings-text-1")
        )
    }

    override fun name(): String {
        return SCREEN_IDENTIFIER
    }

    companion object {
        const val SCREEN_IDENTIFIER: String = "settings"
    }
}
