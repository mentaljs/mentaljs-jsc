package com.openland.open.modules

import com.openland.mentaljs.jsc.modules.MentalMethod
import com.openland.mentaljs.jsc.modules.MentalNativeModule
import com.openland.mentaljs.jsc.modules.MentalRuntime
import com.openland.open.EventEmitter
import com.openland.open.view.OpenRootView

class AttachRootEvent(val id: Int, val name: String)
class DetachhRootEvent(val id: Int)

class UIManager : MentalNativeModule("UIManager") {

    private lateinit var eventEmitter: EventEmitter
    private var nextRootId: Int = 1
    private var views = mutableMapOf<Int, OpenRootView>()

    override fun initialize(runtime: MentalRuntime) {
        this.eventEmitter = EventEmitter("AppRegistry", runtime)
    }

    fun attachRootView(name: String, view: OpenRootView): Int {
        val id = this.nextRootId++
        this.views[id] = view
        this.eventEmitter.postEvent("start", AttachRootEvent(id, name))
        return id
    }

    fun detachRootView(id: Int) {
        this.eventEmitter.postEvent("stop", DetachhRootEvent(id))
        this.views.remove(id)
    }

    @MentalMethod
    fun initView(id: Int, spec: String) {

    }

    @MentalMethod
    fun updateView(id: Int, spec: String) {

    }
}