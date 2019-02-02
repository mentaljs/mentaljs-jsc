package com.openland.open.modules

import android.util.Log
import com.beust.klaxon.Klaxon
import com.openland.open.*
import com.openland.open.view.*

class AttachRootEvent(val id: Int, val name: String)
class DetachRootEvent(val id: Int)

@MentalModule
class UIManager : MentalNativeModule("UIManager") {

    private lateinit var eventEmitter: EventEmitter
    private var inited = false
    private var nextRootId: Int = 1
    private var views = mutableMapOf<Int, OpenRootView>()
    private var pending = arrayListOf<AttachRootEvent>()
    private lateinit var runtime: MentalRuntime

    override fun initialize(runtime: MentalRuntime) {
        this.runtime = runtime

        ViewResolver.registerView("XView", XViewProps::class) { ctx, props, children, runtime ->
            XView.create(ctx)
                    .spec(props as XViewProps)
                    .children(children)
                    .runtime(runtime)
                    .build()

        }
    }

    override fun started(runtime: MentalRuntime) {
        this.eventEmitter = EventEmitter("AppRegistry", runtime)
        this.inited = true
        for (p in pending) {
            this.eventEmitter.postEvent("start", p)
        }
    }

    fun attachRootView(name: String, view: OpenRootView): Int {
        val id = this.nextRootId++
        this.views[id] = view
        if (!this.inited) {
            this.pending.add(AttachRootEvent(id, name))
        } else {
            this.eventEmitter.postEvent("start", AttachRootEvent(id, name))
        }
        return id
    }

    fun detachRootView(id: Int) {
        this.eventEmitter.postEvent("stop", DetachRootEvent(id))
        this.views.remove(id)
    }

    @MentalMethod
    fun initView(id: Int, spec: String) {
        runtime.runOnWorkerThread {
            Log.d("UIManager", "View inited")
            Log.d("UIManager", spec)
            val specValue = Klaxon().parse<ViewSpec>(spec)!!
            val view = this.views[id]
            Log.d("UIManager", "Parsed")
            view?.setConfig(specValue)
        }
    }

    @MentalMethod
    fun updateView(id: Int, spec: String) {
        runtime.runOnWorkerThread {
            Log.d("UIManager", "View updated")
            val specValue = Klaxon().parse<ViewSpec>(spec)!!
            val view = this.views[id]
            view?.setConfig(specValue)
        }
    }
}