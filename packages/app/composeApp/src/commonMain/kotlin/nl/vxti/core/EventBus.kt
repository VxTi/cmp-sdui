package nl.vxti.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import nl.vxti.common.events.models.IEvent

object EventBus {
    private val _events = MutableSharedFlow<IEvent>()
    val events = _events.asSharedFlow()

    fun emit(vararg events: List<IEvent>) {
        events.forEach { emit(it) }
    }
}
