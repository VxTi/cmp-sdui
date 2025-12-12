package nl.vxti.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *                 Event definitions                   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
@Serializable
sealed class Event(
    override val _type: String
) : TypedObject()

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
) : Event(EventType.NAVIGATION)