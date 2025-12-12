package nl.vxti.sdui.screen

import kotlinx.serialization.Serializable
import nl.vxti.common.ServerComponent
import nl.vxti.common.TextComponent
import nl.vxti.core.AppRequestContext

@org.springframework.stereotype.Component
@Serializable
class ProfileScreen : ScreenInstance {

    override fun content(context: AppRequestContext): List<ServerComponent> {
        return listOf(
            TextComponent("test", contentId = "text-comp")
        )
    }

    override fun name(): String {
        return SCREEN_IDENTIFIER
    }

    companion object {
        const val SCREEN_IDENTIFIER: String = "profile"
    }
}
