package com.openland.mentaljs.jsc.runtime.modules

import android.os.Handler
import com.openland.mentaljs.jsc.runtime.*

interface JSTimer : MentalJSModule {
    fun runCallbacks(ids: List<Int>)
}

class TimerModule : MentalNativeModule("Timer") {

    private lateinit var runtime: MentalRuntime
    private lateinit var jsTimer: JSTimer
    private lateinit var handler: Handler

    override fun initialize(runtime: MentalRuntime) {
        this.runtime = runtime
        this.jsTimer = runtime.getJsModule()
        this.handler = Handler(runtime.looper, Handler.Callback {
            this.jsTimer.runCallbacks(listOf(it.what))
            true
        })
    }

    @MentalMethod
    fun setTimeout(id: Int, duration: Int) {
        handler.sendEmptyMessageDelayed(id, duration.toLong())
    }

    @MentalMethod
    fun clearTimeout(id: Int) {
        handler.removeMessages(id)
    }
}