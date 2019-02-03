package com.openland.react

import org.json.JSONObject

interface EventEmitterJS : JavaScriptModule {
    fun postMessage(name: String, event: String, args: String)
}

@MentalModule
class EventEmitterModule : NativeModule("EventEmitter") {

    private lateinit var emitter: EventEmitterJS
    private lateinit var runtime: JavaScriptRuntime

    override fun initialize(runtime: JavaScriptRuntime) {
        this.emitter = runtime.getJsModule()
        this.runtime = runtime
    }

    fun postMessage(name: String, event: String, args: JSONObject?) {
        this.runtime.postToJsThread {
            emitter.postMessage(name, event, args?.toString() ?: "{}")
        }
    }
}