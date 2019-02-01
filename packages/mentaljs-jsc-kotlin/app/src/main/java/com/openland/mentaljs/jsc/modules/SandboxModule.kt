package com.openland.mentaljs.jsc.modules

import com.openland.open.EventEmitter

class SandboxModule : MentalNativeModule("SandboxModule") {

    private lateinit var eventEmitter: EventEmitter

    override fun initialize(runtime: MentalRuntime) {
        this.eventEmitter = EventEmitter("sandbox", runtime)
    }

    @MentalMethod
    fun method() {
        this.eventEmitter.postEvent("sample")
    }
}