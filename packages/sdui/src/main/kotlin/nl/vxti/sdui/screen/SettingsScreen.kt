package nl.vxti.sdui.screen

import nl.vxti.common.ServerComponent
import nl.vxti.common.TextComponent
import nl.vxti.core.AppRequestContext

@org.springframework.stereotype.Component
class SettingsScreen : ScreenInstance {

    override fun content(context: AppRequestContext): List<ServerComponent> {
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
