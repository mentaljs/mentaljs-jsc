package com.openland.open

import com.openland.open.modules.EventEmitterModule

class EventEmitter(val name: String, private val runtime: MentalRuntime) {
    private val module by lazy { runtime.getNativeModule<EventEmitterModule>() }

    fun postEvent(event: String) {
        module.postMessage(name, event, null)
    }

    fun postEvent(event: String, data: Any) {
        module.postMessage(name, event, data)
    }
}