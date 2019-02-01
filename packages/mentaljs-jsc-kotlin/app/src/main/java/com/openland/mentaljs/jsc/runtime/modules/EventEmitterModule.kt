package com.openland.mentaljs.jsc.runtime.modules

import com.openland.mentaljs.jsc.runtime.MentalJSModule
import com.openland.mentaljs.jsc.runtime.MentalNativeModule
import com.openland.mentaljs.jsc.runtime.MentalRuntime
import com.openland.mentaljs.jsc.runtime.getJsModule

interface EventEmitterJS : MentalJSModule {
    fun postMessage(name: String, event: String, args: Any?)
}

class EventEmitterModule : MentalNativeModule("EventEmitter") {

    private lateinit var emitter: EventEmitterJS

    override fun initialize(runtime: MentalRuntime) {
        this.emitter = runtime.getJsModule()
    }

    fun postMessage(name: String, event: String, args: Any?) {
        emitter.postMessage(name, event, args)
    }
}