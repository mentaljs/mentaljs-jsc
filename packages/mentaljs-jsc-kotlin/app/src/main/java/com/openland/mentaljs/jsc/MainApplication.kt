package com.openland.mentaljs.jsc

import com.openland.mentaljs.jsc.modules.SandboxModule
import com.openland.mentaljs.jsc.modules.BindingBenchmarkModule
import com.openland.mentaljs.jsc.modules.MentalNativeModule
import com.openland.open.OpenNativeApplication

class MainApplication : OpenNativeApplication() {

    override fun getModules(): Collection<MentalNativeModule> {
        return listOf(
                SandboxModule(),
                BindingBenchmarkModule()
        )
    }
}