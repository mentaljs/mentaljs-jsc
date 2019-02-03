package com.openland.react

interface JSTimer : JavaScriptModule {
    fun runCallbacks(ids: List<Int>)
}

interface TimerHandler {
    fun setTimerCallback(callback: (IntArray) -> Unit)
    fun setTimeout(id: Int, duration: Int)
    fun clearTimeout(id: Int)
}

@MentalModule
class TimerModule(private val handler: TimerHandler) : NativeModule("Timer") {

    private lateinit var runtime: JavaScriptRuntime
    private lateinit var jsTimer: JSTimer
    private var timers = mutableMapOf<Int, Int>()

    override fun initialize(runtime: JavaScriptRuntime) {
        this.runtime = runtime
        this.jsTimer = runtime.getJsModule()
        handler.setTimerCallback {
            for (t in it) {
                val ex = this.timers[t]
                if (ex !== null) {
                    if (ex > 0) {
                        this.handler.setTimeout(t, ex)
                    }
                    this.jsTimer.runCallbacks(listOf(t))
                }
            }
        }
//        this.handler = Handler(runtime.looper, Handler.Callback {
//            val ex = this.timers[it.what]
//            if (ex !== null) {
//                if (ex > 0) {
//                    handler.sendEmptyMessageDelayed(it.what, ex.toLong())
//                }
//                this.jsTimer.runCallbacks(listOf(it.what))
//            }
//            true
//        })
    }

    @MentalMethod
    fun setTimeout(id: Int, duration: Int) {
        this.timers[id] = 0
        this.handler.setTimeout(id, duration)
    }

    @MentalMethod
    fun setInterval(id: Int, duration: Int) {
        this.timers[id] = duration
        this.handler.setTimeout(id, duration)
    }

    @MentalMethod
    fun clearTimeout(id: Int) {
        this.timers.remove(id)
        this.handler.clearTimeout(id)
    }
}