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
    private var timers = mutableMapOf<Int, Int>()

    override fun initialize(runtime: MentalRuntime) {
        this.runtime = runtime
        this.jsTimer = runtime.getJsModule()
        this.handler = Handler(runtime.looper, Handler.Callback {
            val ex = this.timers[it.what]
            if (ex !== null) {
                if (ex > 0) {
                    handler.sendEmptyMessageDelayed(it.what, ex.toLong())
                }
                this.jsTimer.runCallbacks(listOf(it.what))
            }
            true
        })
    }

    @MentalMethod
    fun setTimeout(id: Int, duration: Int) {
        this.timers[id] = 0
        handler.sendEmptyMessageDelayed(id, duration.toLong())
    }

    @MentalMethod
    fun setInterval(id: Int, duration: Int) {
        this.timers[id] = duration
        handler.sendEmptyMessageDelayed(id, duration.toLong())
    }

    @MentalMethod
    fun clearTimeout(id: Int) {
        this.timers.remove(id)
        handler.removeMessages(id)
    }
}