package com.openland.mentaljs.jsc

import com.openland.mentaljs.jsc.runtime.EventEmitter
import com.openland.mentaljs.jsc.runtime.MentalMethod
import com.openland.mentaljs.jsc.runtime.MentalNativeModule
import com.openland.mentaljs.jsc.runtime.MentalRuntime

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