package nl.vxti.common.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.vxti.common.components.models.ComponentType
import nl.vxti.common.components.models.IInteractable
import nl.vxti.common.components.models.IComponent
import nl.vxti.common.events.models.IEvent

@Serializable
@SerialName(ComponentType.BUTTON)
data class ButtonComponent(
    val text: String,
    val variant: ButtonVariant,
    override val interactionEvents: List<IEvent>,
    override val contentId: String
) : IComponent, IInteractable {
    override val _type: String = ComponentType.BUTTON
}

enum class ButtonVariant {
    NORMAL,
    SECONDARY,
    DISABLED,
}