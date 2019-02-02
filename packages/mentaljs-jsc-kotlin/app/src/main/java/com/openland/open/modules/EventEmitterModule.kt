package com.openland.open.modules

import com.beust.klaxon.Klaxon
import com.openland.open.MentalJSModule
import com.openland.open.MentalNativeModule
import com.openland.open.MentalRuntime
import com.openland.open.getJsModule

interface EventEmitterJS : MentalJSModule {
    fun postMessage(name: String, event: String, args: String)
}

class EventEmitterModule : MentalNativeModule("EventEmitter") {

    private lateinit var emitter: EventEmitterJS
    private lateinit var runtime: MentalRuntime

    override fun initialize(runtime: MentalRuntime) {
        this.emitter = runtime.getJsModule()
        this.runtime = runtime
    }

    fun postMessage(name: String, event: String, args: Any?) {
        this.runtime.runOnJsThread {
            emitter.postMessage(name, event, Klaxon().toJsonString(args))
        }
    }
}