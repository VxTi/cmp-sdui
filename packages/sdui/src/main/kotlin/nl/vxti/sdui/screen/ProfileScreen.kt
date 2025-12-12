package nl.vxti.sdui.screen

import kotlinx.serialization.Serializable
import nl.vxti.common.components.TextComponent
import nl.vxti.common.components.models.IComponent
import nl.vxti.core.AppRequestContext
import nl.vxti.sdui.screen.models.IScreen

@org.springframework.stereotype.Component
@Serializable
class ProfileScreen : IScreen {

    override fun content(context: AppRequestContext): List<IComponent> {
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
