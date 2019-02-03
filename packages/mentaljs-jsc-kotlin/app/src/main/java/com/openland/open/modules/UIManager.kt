package com.openland.open.modules

import android.util.Log
import com.openland.open.view.*
import com.openland.react.*

class AttachRootEvent(val id: Int, val name: String)
class DetachRootEvent(val id: Int)

@MentalModule
class UIManager : NativeModule("UIManager") {

    private lateinit var eventEmitter: com.openland.react.EventEmitter
    private var inited = false
    private var nextRootId: Int = 1
    private var views = mutableMapOf<Int, OpenRootView>()
    private var pending = arrayListOf<AttachRootEvent>()
    private lateinit var runtime: JavaScriptRuntime

    override fun initialize(runtime: JavaScriptRuntime) {
        this.runtime = runtime

        ViewResolver.registerView("XView", XViewProps::class) { ctx, props, children, reactContext ->
            XView.create(ctx)
                    .spec(props as XViewProps)
                    .children(children)
                    .reactContext(reactContext)
                    .build()

        }
    }

    override fun started(runtime: JavaScriptRuntime) {
        this.eventEmitter = com.openland.react.EventEmitter("AppRegistry", runtime)
        this.inited = true
        for (p in pending) {
            this.eventEmitter.postEvent("start", p)
        }
    }

    fun attachRootView(name: String, view: OpenRootView): Int {
        Log.d("UIManager", "Attach root view")
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
        Log.d("UIManager", "View inited")
        val view = this.views[id]
        val start = System.currentTimeMillis()
        val s = ViewResolver.parseViewSpec(spec)
        Log.d("UIManager", "View ${System.currentTimeMillis() - start} ms")
        view?.setConfig(s)
    }

    @MentalMethod
    fun updateView(id: Int, spec: String) {
        Log.d("UIManager", "View updated")
        val view = this.views[id]
        view?.setConfig(ViewResolver.parseViewSpec(spec))
    }
}