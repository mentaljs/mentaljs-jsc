package com.openland.react

import kotlin.reflect.KClass

interface JavaScriptRuntime {

    // Modules

    fun registerNativeModule(module: NativeModule)
    fun <T : JavaScriptModule> getJsModule(clazz: KClass<T>): T
    fun <T : NativeModule> getNativeModule(clazz: KClass<T>): T

    // Lifecycle

    fun postToJsThread(callback: () -> Unit)
    fun start()
    fun start(source: String)
    fun started()
    fun destroy()
}

inline fun <reified T : JavaScriptModule> JavaScriptRuntime.getJsModule(): T {
    return this.getJsModule(T::class)
}

inline fun <reified T : NativeModule> JavaScriptRuntime.getNativeModule(): T {
    return this.getNativeModule(T::class)
}