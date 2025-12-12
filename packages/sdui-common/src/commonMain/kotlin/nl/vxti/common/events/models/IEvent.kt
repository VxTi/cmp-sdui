package nl.vxti.common.events.models

import kotlinx.serialization.Serializable
import nl.vxti.common.IIdentifiable

@Serializable
abstract class IEvent(
    override val _type: String
) : IIdentifiable