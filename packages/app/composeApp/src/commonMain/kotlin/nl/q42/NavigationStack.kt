package me.vxti

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.vxti.common.screen.Screen

internal class NavigationStack {
    private val _stack = mutableStateListOf<Screen>()

    val stack: SnapshotStateList<Screen> = _stack

    fun push(screen: Screen) {
        _stack.add(screen)
    }

    /**
     * Removes the last screen from the stack (if any) and adds the new screen.
     */
    fun pruneAndPush(screen: Screen) {
        if (_stack.isNotEmpty()) {
            _stack.removeLastOrNull()
        }
        _stack.add(screen)
    }

    fun pop(): Screen? {
        if (_stack.isEmpty()) return null
        return _stack.removeLastOrNull()
    }

    fun currentScreen(): Screen? {
        return _stack.lastOrNull()
    }
}
    