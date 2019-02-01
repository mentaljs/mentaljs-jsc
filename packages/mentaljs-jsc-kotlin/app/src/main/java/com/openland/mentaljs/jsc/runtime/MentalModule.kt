package com.openland.mentaljs.jsc.runtime

import android.os.Looper
import kotlin.reflect.KClass

interface MentalRuntime {

    // Threads
    val looper: Looper

    // Execution

    fun registerNativeModule(module: MentalNativeModule)

    fun <T : MentalJSModule> getJsModule(clazz: KClass<T>): T
    fun <T : MentalNativeModule> getNativeModule(clazz: KClass<T>): T

    fun start(source: String)
    fun destroy()
}

inline fun <reified T : MentalJSModule> MentalRuntime.getJsModule(): T {
    return this.getJsModule(T::class)
}

inline fun <reified T : MentalNativeModule> MentalRuntime.getNativeModule(): T {
    return this.getNativeModule(T::class)
}

annotation class MentalMethod(val name: String = "")

abstract class MentalNativeModule(val name: String) {
    open fun initialize(runtime: MentalRuntime) {

    }
}

interface MentalJSModule {

}