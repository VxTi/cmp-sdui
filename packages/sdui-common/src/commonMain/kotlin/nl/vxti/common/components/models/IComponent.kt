package nl.vxti.common.components.models

import nl.vxti.common.IIdentifiable
import nl.vxti.common.events.models.IEvent

interface IInteractable {
    val interactionEvents: List<IEvent>;
}

interface IComponent : IIdentifiable {
    val contentId: String
}