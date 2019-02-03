package com.openland.mentaljs.jsc.modules

import com.openland.react.MentalMethod
import com.openland.react.MentalModule
import com.openland.react.NativeModule

@MentalModule
class BindingBenchmarkModule : NativeModule("BindingBenchmarking") {

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