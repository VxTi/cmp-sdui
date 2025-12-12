package me.vxti.sdui.screen

import me.vxti.common.ServerComponent
import me.vxti.common.TextComponent
import me.vxti.core.AppRequestContext

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
