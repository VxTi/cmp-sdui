package me.vxti.sdui.screen

import kotlinx.serialization.Serializable
import me.vxti.common.ServerComponent
import me.vxti.common.TextComponent
import me.vxti.core.AppRequestContext

@org.springframework.stereotype.Component
@Serializable
class SearchScreen : ScreenInstance {

    override fun content(context: AppRequestContext): List<ServerComponent> {
        return listOf(
            TextComponent("Search screen", contentId = "Test")
        )
    }

    override fun name(): String {
        return SCREEN_IDENTIFIER
    }

    companion object {
        const val SCREEN_IDENTIFIER: String = "search"
    }
}
