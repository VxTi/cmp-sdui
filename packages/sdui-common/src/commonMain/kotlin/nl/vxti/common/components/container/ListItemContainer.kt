package nl.vxti.common.components.container

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.vxti.common.components.models.ComponentType
import nl.vxti.common.components.models.IComponent
import nl.vxti.common.components.models.ListItem

@Serializable
@SerialName(ComponentType.LIST_ITEM_CONTAINER)
data class ListItemContainer(
    val items: List<ListItem>,
    val title: String?,
    override val contentId: String
) : IComponent {
    override val _type: String = ComponentType.LIST_ITEM_CONTAINER
}