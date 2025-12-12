package me.vxti.core

import androidx.compose.ui.input.key.Key
import io.ktor.util.date.getTimeMillis


class CacheSet<T>() {
    private val map = mutableMapOf<String, CacheEntry<T>>()

    fun get(key: String): T? {
        val entry = map[key]

        if (expired(key)) {
            map.remove(key)
            return null
        }

        return entry?.data
    }

    fun containsKey(key: String): Boolean {
        return map.containsKey(key) && !expired(key)
    }

    fun expired(key: String): Boolean {
        val entry = map[key] ?: return true;
        return (getTimeMillis() - entry.timestamp) > entry.expiresIn
    }

    fun put(key: String, data: T, expiresIn: Long) {
        map[key] = CacheEntry(data, expiresIn)
    }

    fun clear() {
        map.clear()
    }
}

data class CacheEntry<T>(
    val data: T,
    val expiresIn: Long,
    val timestamp: Long = getTimeMillis(),
)
