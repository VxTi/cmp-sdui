package nl.vxti.common.components.container

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.vxti.common.components.models.ComponentType
import nl.vxti.common.components.models.IComponent

@Serializable
@SerialName(ComponentType.SCROLLABLE_CONTAINER)
data class CarouselContainerComponent(
    override val _type: String = ComponentType.SCROLLABLE_CONTAINER,
    val content: List<IComponent>,
    override val contentId: String
) : IComponent