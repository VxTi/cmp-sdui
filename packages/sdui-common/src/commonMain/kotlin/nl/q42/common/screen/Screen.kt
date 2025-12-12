package me.vxti.common.screen

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import me.vxti.common.ServerComponent

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
    val content: List<@Polymorphic ServerComponent>
    val id: String


    constructor(
        name: String,
        content: List<@Polymorphic ServerComponent>,
        cacheDurationMs: Long = defaultScreenCacheExpirationDurationMs
    ) {
        this.id = name
        this.content = content
        this.hash = content.hashCode();
        this.cacheDurationMs = cacheDurationMs
    }

    constructor(name: String, vararg content: ServerComponent) : this(name, content.toList())

    fun getComponentById(contentId: String): ServerComponent? {
        for (component in content) {
            if (component.contentId == contentId) {
                return component
            }
        }
        return null
    }
}
