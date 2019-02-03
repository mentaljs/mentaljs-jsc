package com.openland.react.ui

import android.util.Log
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.jr.ob.JSON
import com.fasterxml.jackson.jr.stree.JacksonJrsTreeCodec
import com.fasterxml.jackson.jr.stree.JrsArray
import com.fasterxml.jackson.jr.stree.JrsObject
import com.fasterxml.jackson.jr.stree.JrsString
import com.openland.react.*
import org.json.JSONObject

@MentalModule
class UIManager(val ctx: ReactContext, val viewFactories: Collection<NativeViewFactory<*>>) : NativeModule("UIManager") {

    private lateinit var eventEmitter: com.openland.react.EventEmitter
    private lateinit var viewEventEmitter: EventEmitter
    private var inited = false
    private var nextRootId: Int = 1
    private var views = mutableMapOf<Int, ReactRootView>()
    private var pending = arrayListOf<JSONObject>()
    private lateinit var runtime: JavaScriptRuntime
    private val nativeViewRepository = NativeViewRepository()
    private val json = JSON.std.with(JacksonJrsTreeCodec())
    private val callbackHandler = object : CallbackHandler {
        override fun invoke(key: String, args: JSONObject) {
            reportCallback(key, args)
        }
    }

    //
    // Initialization
    //

    override fun initialize(runtime: JavaScriptRuntime) {
        this.runtime = runtime

        for (v in viewFactories) {
            nativeViewRepository.registerViewFactory(v)
        }
    }

    override fun started(runtime: JavaScriptRuntime) {
        this.eventEmitter = EventEmitter("AppRegistry", runtime)
        this.viewEventEmitter = EventEmitter("UIManager", runtime)
        this.inited = true
        for (p in pending) {
            this.eventEmitter.postEvent("start", p)
        }
    }

    //
    // Root View
    //

    fun attachRootView(name: String, view: ReactRootView): Int {
        Log.d("UIManager", "Attach root view")
        val id = this.nextRootId++
        this.views[id] = view
        if (!this.inited) {
            this.pending.add(JSONObject().apply {
                put("id", id)
                put("name", name)
            })
        } else {
            this.eventEmitter.postEvent("start", JSONObject().apply {
                put("id", id)
                put("name", name)
            })
        }
        return id
    }

    fun detachRootView(id: Int) {
        this.eventEmitter.postEvent("stop", JSONObject().apply {
            put("id", id)
        })
        this.views.remove(id)
    }

    //
    // View Resolving
    //

    fun resolveSpec(spec: String): ViewSpec {
        val node = json.treeFrom<TreeNode>(spec)
        return resolveSpec(node)
    }

    fun resolveSpec(node: TreeNode): ViewSpec {
        if (!node.isObject) {
            throw Error("")
        }
        val obj = node as JrsObject
        val key = (obj.get("key") as JrsString).value
        val type = (obj.get("type") as JrsString).value
        val children = (obj.get("children") as JrsArray)
        val propsSerializer = this.nativeViewRepository.findPropsSerializer(type)
        val props = propsSerializer.parse(obj.get("props"), this.callbackHandler)

        val ch = mutableListOf<ViewSpec>()
        for (i in 0 until children.size()) {
            ch.add(resolveSpec(children.get(i)))
        }
        return ViewSpec(type, key, props, ch)
    }

    fun resolveView(context: ComponentContext, spec: ViewSpec): Component {
        return this.nativeViewRepository.findViewFactory(spec.type).createView(context, spec.props, spec.children.toTypedArray(), this.ctx)
    }

    //
    // View Callbacks
    //

    fun reportCallback(key: String, args: JSONObject) {
        this.viewEventEmitter.postEvent("event", JSONObject().apply {
            put("key", key)
            put("args", args)
        })
    }

    //
    // Update Handling
    //

    @MentalMethod
    fun initView(id: Int, spec: String) {
        Log.d("UIManager", "View inited")
        val view = this.views[id]
        val start = System.currentTimeMillis()
        val s = resolveSpec(spec)
        Log.d("UIManager", "View ${System.currentTimeMillis() - start} ms")
        view?.setConfig(s)
    }

    @MentalMethod
    fun updateView(id: Int, spec: String) {
        Log.d("UIManager", "View updated")
        val view = this.views[id]
        val start = System.currentTimeMillis()
        val s = resolveSpec(spec)
        Log.d("UIManager", "View ${System.currentTimeMillis() - start} ms")
        view?.setConfig(s)
    }
}