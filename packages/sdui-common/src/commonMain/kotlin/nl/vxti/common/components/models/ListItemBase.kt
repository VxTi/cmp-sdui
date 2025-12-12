package nl.vxti.common.components.models

import kotlinx.serialization.Serializable
import nl.vxti.common.IIdentifiable
import nl.vxti.common.events.models.IEvent

@Serializable
sealed class ListItem: IIdentifiable, IInteractable {
    abstract val itemId: String
    abstract override val interactionEvents: List<IEvent>
}