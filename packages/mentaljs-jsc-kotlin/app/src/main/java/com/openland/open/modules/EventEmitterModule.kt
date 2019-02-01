package com.openland.open.modules

import com.beust.klaxon.Klaxon
import com.openland.mentaljs.jsc.modules.MentalJSModule
import com.openland.mentaljs.jsc.modules.MentalNativeModule
import com.openland.mentaljs.jsc.modules.MentalRuntime
import com.openland.mentaljs.jsc.modules.getJsModule

interface EventEmitterJS : MentalJSModule {
    fun postMessage(name: String, event: String, args: String)
}

class EventEmitterModule : MentalNativeModule("EventEmitter") {

    private lateinit var emitter: EventEmitterJS

    override fun initialize(runtime: MentalRuntime) {
        this.emitter = runtime.getJsModule()
    }

    fun postMessage(name: String, event: String, args: Any?) {
        emitter.postMessage(name, event, Klaxon().toJsonString(args))
    }
}