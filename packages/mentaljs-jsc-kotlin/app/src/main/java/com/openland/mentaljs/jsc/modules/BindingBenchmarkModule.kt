package com.openland.mentaljs.jsc.modules

import com.eclipsesource.v8.V8Object
import com.openland.open.engine.MentalRuntimeV8

class BindingBenchmarkModule : MentalNativeModule("BindingBenchmarking") {
    override fun initialize(runtime: MentalRuntime) {
        val r = runtime as MentalRuntimeV8
        val obj = r.nativeModules.get("BindingBenchmarking") as V8Object
        obj.registerJavaMethod({ self, args ->
            // Do nothing
        }, "noReflectionCallback")
    }

    @MentalMethod
    fun method() {
        // Do nothing
    }

    @MentalMethod
    fun serializedMethod(src: String) {
        // Do nothing
    }

    @MentalMethod
    fun argsMethod(a: String, b: Int) {
        // Do nothing
    }
}