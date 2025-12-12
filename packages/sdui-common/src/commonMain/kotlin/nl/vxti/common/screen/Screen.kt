package nl.vxti.common.screen

import kotlinx.serialization.Serializable
import nl.vxti.common.components.models.IComponent

private const val defaultScreenCacheExpirationDurationMs = 5 * 60 * 1000L

@Serializable
class Screen {
    /**
     * Duration in milliseconds for which the screen should be cached.
     * Defaults to 5 minutes (300,000 ms).
     * When set to <= 0, the screen will not be cached.
     */
    val cacheDurationMs: Long;
    val hash: Int
    val content: List<IComponent>
    val id: String


    constructor(
        name: String,
        content: List<IComponent>,
        cacheDurationMs: Long = defaultScreenCacheExpirationDurationMs
    ) {
        this.id = name
        this.content = content
        this.hash = content.hashCode();
        this.cacheDurationMs = cacheDurationMs
    }

    constructor(name: String, vararg content: IComponent) : this(name, content.toList())

    fun findComponentById(contentId: String): IComponent? {
        for (component in content) {
            if (component.contentId == contentId) {
                return component
            }
        }
        return null
    }
}
