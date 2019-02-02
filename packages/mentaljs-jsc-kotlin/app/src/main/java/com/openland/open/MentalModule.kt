package com.openland.open

import android.os.Looper
import kotlin.reflect.KClass

interface MentalRuntime {

    // Threads
    val looper: Looper

    // Execution

    fun registerNativeModule(module: MentalNativeModule)

    fun <T : MentalJSModule> getJsModule(clazz: KClass<T>): T
    fun <T : MentalNativeModule> getNativeModule(clazz: KClass<T>): T

    fun start()
    fun start(source: String)
    fun started()
    fun runOnJsThread(callback: () -> Unit)
    fun runOnWorkerThread(callback: () -> Unit)
    fun destroy()
}

inline fun <reified T : MentalJSModule> MentalRuntime.getJsModule(): T {
    return this.getJsModule(T::class)
}

inline fun <reified T : MentalNativeModule> MentalRuntime.getNativeModule(): T {
    return this.getNativeModule(T::class)
}

abstract class MentalNativeModule(val name: String) {
    open fun initialize(runtime: MentalRuntime) {

    }

    open fun started(runtime: MentalRuntime) {

    }
}

interface MentalJSModule {

}