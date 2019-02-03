package com.openland.react

import org.json.JSONObject


class EventEmitter(val name: String, private val runtime: JavaScriptRuntime) {
    private val module by lazy { runtime.getNativeModule<EventEmitterModule>() }

    fun postEvent(event: String) {
        module.postMessage(name, event, null)
    }

    fun postEvent(event: String, data: JSONObject) {
        module.postMessage(name, event, data)
    }
}