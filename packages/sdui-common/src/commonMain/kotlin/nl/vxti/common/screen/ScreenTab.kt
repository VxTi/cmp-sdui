package nl.vxti.common.screen

import kotlinx.serialization.Serializable
import nl.vxti.common.screen.models.ScreenRoute

@Serializable
data class ScreenTab(
    val title: String,
    val imageUrl: String?,
    val screenId: String,
) {
    constructor(
        title: String,
        imageUrl: String?,
        route: ScreenRoute
    ) : this(title, imageUrl, route.path)
}
