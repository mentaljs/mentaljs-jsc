package com.openland.mentaljs.jsc.modules

import com.openland.react.*

@MentalModule
class SandboxModule : NativeModule("SandboxModule") {

    private lateinit var eventEmitter: com.openland.react.EventEmitter

    override fun initialize(runtime: JavaScriptRuntime) {
        this.eventEmitter = com.openland.react.EventEmitter("sandbox", runtime)
    }

    @MentalMethod
    fun method() {
        this.eventEmitter.postEvent("sample")
    }
}