package com.openland.mentaljs.jsc

import com.openland.mentaljs.jsc.modules.SandboxModule
import com.openland.mentaljs.jsc.modules.BindingBenchmarkModule
import com.openland.open.OpenNativeApplication
import com.openland.react.NativeModule

class MainApplication : OpenNativeApplication() {

    override fun getModules(): Collection<NativeModule> {
        return listOf(
                SandboxModule(),
                BindingBenchmarkModule()
        )
    }
}