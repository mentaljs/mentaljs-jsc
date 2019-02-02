package com.openland.open.engine

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.utils.V8ObjectUtils
import com.openland.open.MentalJSModule
import com.openland.open.MentalMethod
import com.openland.open.MentalNativeModule
import com.openland.open.MentalRuntime
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

class MentalRuntimeV8 : MentalRuntime {

    private var modules = mutableListOf<MentalNativeModule>()
    private var modulesMap = mutableMapOf<KClass<*>, MentalNativeModule>()
    lateinit var runtime: V8
    lateinit var nativeModules: V8Object
    lateinit var jsModules: V8Object
    private val thread = HandlerThread("v8")
    override val looper: Looper
    private val handler: Handler


    init {
        thread.start()
        while (thread.looper == null) {
            Thread.sleep(1)
        }
        looper = thread.looper
        this.handler = Handler(looper)
    }

    override fun start() {
        this.handler.post {
            runtime = V8.createV8Runtime("global")
            nativeModules = V8Object(runtime)
            runtime.add("NativeModules", nativeModules)

            jsModules = V8Object(runtime)
            runtime.add("JSModules", jsModules)

            for (module in modules) {
                val v8Object = V8Object(this.runtime)
                module::class.members.filter { it.annotations.any { it is MentalMethod } }.forEach {
                    val annotation = it.annotations.find { it is MentalMethod }!! as MentalMethod
                    var name = it.name
                    if (annotation.name != "") {
                        name = annotation.name
                    }
                    v8Object.registerJavaMethod(module, it.name, name, it.parameters.filter { it.kind === KParameter.Kind.VALUE }.map { (it.type.classifier!! as KClass<*>).java }.toTypedArray())
                }
                nativeModules.add(module.name, v8Object)
            }

            for (module in modules) {
                module.initialize(this)
            }
        }
    }

    override fun started() {
        this.handler.post {
            for (module in modules) {
                module.started(this)
            }
        }
    }

    override fun runOnJsThread(callback: () -> Unit) {
        this.handler.post(callback)
    }

    override fun <T : MentalJSModule> getJsModule(clazz: KClass<T>): T {
        return Proxy.newProxyInstance(clazz.java.classLoader, arrayOf(clazz.java)) { proxy, method, args ->
            if (!thread.looper.isCurrentThread) {
                throw Error("JS modules need to be executed on JS thread")
            }
            val obj = jsModules.getObject(clazz.simpleName)
            if (obj.isUndefined) {
                throw Error("Unable to find js module " + clazz.simpleName)
            }
            obj.executeVoidFunction(method.name, V8ObjectUtils.toV8Array(this.runtime, args.toList()))
        } as T
    }

    override fun <T : MentalNativeModule> getNativeModule(clazz: KClass<T>): T {
        return this.modulesMap[clazz] as T
    }

    override fun registerNativeModule(module: MentalNativeModule) {
        this.modules.add(module)
        this.modulesMap[module.javaClass.kotlin] = module
    }

    override fun start(source: String) {
        this.handler.post {
            this.runtime.executeVoidScript(source)
        }
    }

    override fun destroy() {
        runtime.shutdownExecutors(false)
    }
}