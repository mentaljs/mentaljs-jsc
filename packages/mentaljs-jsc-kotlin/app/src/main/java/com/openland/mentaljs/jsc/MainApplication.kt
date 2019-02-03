package com.openland.mentaljs.jsc

import com.openland.mentaljs.jsc.modules.SandboxModule
import com.openland.mentaljs.jsc.modules.BindingBenchmarkModule
import com.openland.mentaljs.jsc.modules.BindingBenchmarkModuleSpec
import com.openland.mentaljs.jsc.modules.SandboxModuleSpec
import com.openland.open.OpenNativeApplication
import com.openland.open.modules.UIManager
import com.openland.open.modules.UIManagerSpec
import com.openland.react.NativeModule
import com.openland.react.NativeModuleSpec

class MainApplication : OpenNativeApplication() {

    override fun getModules(): Collection<Pair<NativeModule, NativeModuleSpec>> {
        return listOf(
                UIManager() to UIManagerSpec,
                SandboxModule() to SandboxModuleSpec,
                BindingBenchmarkModule() to BindingBenchmarkModuleSpec
        )
    }
}