package nl.vxti.common.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.vxti.common.components.models.ComponentType
import nl.vxti.common.components.models.IComponent

@Serializable
@SerialName(ComponentType.SEARCH_BAR)
data class SearchBarComponent(
    val placeholder: String?,
    override val contentId: String
) : IComponent {
    override val _type: String = ComponentType.SEARCH_BAR
}