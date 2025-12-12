package nl.vxti.common.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.vxti.common.events.models.EventType
import nl.vxti.common.events.models.IEvent

@Serializable
@SerialName(EventType.NAVIGATION)
data class NavigationEvent(
    val screenId: String,

    /**
     * Whether to prefetch the target screen or not.
     * Prefetching can speed up the navigation significantly, at the cost of higher
     * network strain.
     */
    val prefetch: Boolean = false,
) : IEvent {
    override val _type: String = EventType.NAVIGATION
}