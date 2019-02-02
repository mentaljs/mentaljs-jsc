package com.openland.open.engine

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.eclipsesource.v8.JavaVoidCallback
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.V8Value
import com.eclipsesource.v8.utils.V8ObjectUtils
import com.openland.open.*
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

class MentalRuntimeV8 : MentalRuntime {

    private var modules = mutableListOf<MentalNativeModule>()
    private var modulesMap = mutableMapOf<KClass<*>, MentalNativeModule>()
    lateinit var runtime: V8
    lateinit var nativeModules: V8Object
    lateinit var jsModules: V8Object
    private val thread = HandlerThread("v8-runner")
    private val workerThread = HandlerThread("v8-worker")
    override val looper: Looper
    private val handler: Handler
    private val workerHandler: Handler


    init {
        thread.start()
        workerThread.start()
        while (thread.looper == null || workerThread.looper == null) {
            Thread.sleep(1)
        }
        looper = thread.looper
        this.handler = Handler(looper)
        this.workerHandler = Handler(workerThread.looper)
    }

    override fun start() {
        this.handler.post {
            var start = System.currentTimeMillis()
            runtime = V8.createV8Runtime("global")
            nativeModules = V8Object(runtime)
            runtime.add("NativeModules", nativeModules)

            jsModules = V8Object(runtime)
            runtime.add("JSModules", jsModules)

            Log.d("MentalRuntime", "Engine start time: ${System.currentTimeMillis() - start} ms")
            start = System.currentTimeMillis()

            for (module in modules) {
                start = System.currentTimeMillis()
                val descriptor = Class
                        .forName(module::class.java.name + "_Descriptor")
                        .kotlin
                        .objectInstance as ModuleDescriptor
                Log.d("MentalRuntime", "${module.name} prep time: ${System.currentTimeMillis() - start} ms")
                start = System.currentTimeMillis()
                val v8Object = V8Object(this.runtime)
                for (m in descriptor.getModuleMethods()) {
                    v8Object.registerJavaMethod({ src, args ->
                        val args2 = arrayOfNulls<MethodArgument>(args.length())
                        for (i in 0 until args2.size) {
                            val type = args.getType(i)
                            when (type) {
                                V8Value.STRING -> args2[i] = MethodArgument.StringArgument(args.getString(i))
                                V8Value.INTEGER -> args2[i] = MethodArgument.NumberArgument(args.getInteger(i))
                                else -> throw Error("")
                            }
                        }
                        src.release()
                        args.release()
                        runOnWorkerThread {
                            m.value.invoke(module, args2 as Array<MethodArgument>)
                        }
                    }, m.key)
                }
//                module::class.members.filter { it.annotations.any { it is MentalMethod } }.forEach {
//                    val annotation = it.annotations.find { it is MentalMethod }!! as MentalMethod
//                    var name = it.name
//                    if (annotation.name != "") {
//                        name = annotation.name
//                    }
//                    v8Object.registerJavaMethod(module, it.name, name, it.parameters.filter { it.kind === KParameter.Kind.VALUE }.map { (it.type.classifier!! as KClass<*>).java }.toTypedArray())
//                }
                nativeModules.add(module.name, v8Object)

                Log.d("MentalRuntime", "${module.name} start time: ${System.currentTimeMillis() - start} ms")
                start = System.currentTimeMillis()
            }

            // Log.d("MentalRuntime", "Modules registration time: ${System.currentTimeMillis() - start} ms")
            // start = System.currentTimeMillis()

            for (module in modules) {
                module.initialize(this)
            }

            Log.d("MentalRuntime", "Modules init time: ${System.currentTimeMillis() - start} ms")
            // start = System.currentTimeMillis()
        }
    }

    override fun started() {
        this.handler.post {
            val start = System.currentTimeMillis()
            for (module in modules) {
                module.started(this)
            }
            Log.d("MentalRuntime", "Modules start completed: ${System.currentTimeMillis() - start} ms")
        }
    }

    override fun runOnJsThread(callback: () -> Unit) {
        this.handler.post(callback)
    }

    override fun runOnWorkerThread(callback: () -> Unit) {
        this.workerHandler.post(callback)
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
            val start = System.currentTimeMillis()
            this.runtime.executeVoidScript(source)
            Log.d("MentalRuntime", "Script time: ${System.currentTimeMillis() - start} ms")
        }
    }

    override fun destroy() {
        runtime.shutdownExecutors(false)
    }
}