package com.openland.open.modules

import com.openland.open.MentalMethod
import com.openland.open.MentalNativeModule
import com.openland.open.MentalRuntime
import com.openland.open.EventEmitter
import com.openland.open.view.OpenRootView

class AttachRootEvent(val id: Int, val name: String)
class DetachRootEvent(val id: Int)

class UIManager : MentalNativeModule("UIManager") {

    private lateinit var eventEmitter: EventEmitter
    private var inited = false
    private var nextRootId: Int = 1
    private var views = mutableMapOf<Int, OpenRootView>()
    private var pending = arrayListOf<AttachRootEvent>()

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

    }

    @MentalMethod
    fun updateView(id: Int, spec: String) {

    }
}