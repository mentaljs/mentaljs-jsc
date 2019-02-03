package com.openland.react

abstract class NativeModuleSpec {
    abstract fun getModuleMethods(): Map<String, ModuleMethodInvoker>
}

abstract class NativeModule(val name: String) {
    open fun initialize(runtime: JavaScriptRuntime) {

    }

    open fun started(runtime: JavaScriptRuntime) {

    }
}

interface JavaScriptModule {

}