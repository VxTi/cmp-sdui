package nl.vxti.common

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import nl.vxti.common.analytics.AnalyticEvent
import nl.vxti.common.screen.Screen
import nl.vxti.common.screen.ScreenTab

@Serializable
data class ScreenResponse(
    val screen: Screen,
    var onLoadEvents: List<Event>? = null,
    var onLoadAnalyticEvents: List<AnalyticEvent>? = null,
    var tabs: List<ScreenTab>? = null,
    var metadata: MutableMap<MetadataType, @Contextual Any>? = null
)
