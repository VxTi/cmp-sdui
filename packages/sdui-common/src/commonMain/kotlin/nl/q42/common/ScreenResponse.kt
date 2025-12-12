package me.vxti.common

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import me.vxti.common.analytics.AnalyticEvent
import me.vxti.common.screen.Screen
import me.vxti.common.screen.ScreenTab

@Serializable
data class ScreenResponse(
    val screen: Screen,
    var onLoadEvents: List<Event>? = null,
    var onLoadAnalyticEvents: List<AnalyticEvent>? = null,
    var tabs: List<ScreenTab>? = null,
    var metadata: MutableMap<MetadataType, @Contextual Any>? = null
)
