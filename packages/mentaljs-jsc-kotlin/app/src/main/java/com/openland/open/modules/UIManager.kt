package com.openland.open.modules

import android.util.Log
import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.core.TreeNode
import com.openland.open.*
import com.openland.open.view.*
import org.json.JSONObject
import com.fasterxml.jackson.jr.ob.JSON
import com.fasterxml.jackson.jr.stree.JacksonJrsTreeCodec

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