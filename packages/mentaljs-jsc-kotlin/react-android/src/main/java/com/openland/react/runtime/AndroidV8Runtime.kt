package com.openland.react.runtime

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.V8Value
import com.eclipsesource.v8.utils.V8ObjectUtils
import com.openland.react.*
import java.lang.reflect.Proxy
import kotlin.reflect.KClass

class AndroidV8Runtime(private val jsLooper: Looper, private val workerLooper: Looper) : JavaScriptRuntime {

    private var modules = mutableListOf<NativeModule>()
    private var modulesMap = mutableMapOf<KClass<*>, NativeModule>()
    lateinit var runtime: V8
    lateinit var nativeModules: V8Object
    lateinit var jsModules: V8Object
    private val handler: Handler = Handler(jsLooper)
    private val workerHandler: Handler = Handler(workerLooper)

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
                val descriptor = Class.forName(module::class.qualifiedName + "Spec").newInstance() as NativeModuleSpec
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
                        m.value.invoke(module, args2 as Array<MethodArgument>)
                        this.workerHandler.post {
                            m.value.invoke(module, args2 as Array<MethodArgument>)
                        }
                    }, m.key)
                }
                nativeModules.add(module.name, v8Object)

                Log.d("MentalRuntime", "${module.name} start time: ${System.currentTimeMillis() - start} ms")
                // start = System.currentTimeMillis()
            }

            for (module in modules) {
                start = System.currentTimeMillis()
                module.initialize(this)
                Log.d("MentalRuntime", "${module.name} init time: ${System.currentTimeMillis() - start} ms")
            }

            // Log.d("MentalRuntime", "Modules init time: ${System.currentTimeMillis() - start} ms")
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

    override fun <T : JavaScriptModule> getJsModule(clazz: KClass<T>): T {
        return Proxy.newProxyInstance(clazz.java.classLoader, arrayOf(clazz.java)) { proxy, method, args ->
            if (!jsLooper.isCurrentThread) {
                throw Error("JS modules need to be executed on JS thread")
            }
            val obj = jsModules.getObject(clazz.simpleName)
            if (obj.isUndefined) {
                throw Error("Unable to find js module " + clazz.simpleName)
            }
            val start = System.currentTimeMillis()
            obj.executeVoidFunction(method.name, V8ObjectUtils.toV8Array(this.runtime, args.toList()))
            Log.d("MentalRuntime", "${clazz.simpleName}.${method.name} time: ${System.currentTimeMillis() - start} ms")
        } as T
    }

    override fun postToJsThread(callback: () -> Unit) {
        this.handler.post(callback)
    }

    override fun <T : NativeModule> getNativeModule(clazz: KClass<T>): T {
        return this.modulesMap[clazz] as T
    }

    override fun registerNativeModule(module: NativeModule) {
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