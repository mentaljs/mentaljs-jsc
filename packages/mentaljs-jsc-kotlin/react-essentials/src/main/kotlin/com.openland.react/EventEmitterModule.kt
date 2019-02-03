package com.openland.react

import com.beust.klaxon.Klaxon

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

    fun postMessage(name: String, event: String, args: Any?) {
        this.runtime.postToJsThread {
            emitter.postMessage(name, event, Klaxon().toJsonString(args))
        }
    }
}