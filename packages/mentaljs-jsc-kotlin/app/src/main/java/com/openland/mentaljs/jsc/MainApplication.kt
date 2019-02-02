package com.openland.mentaljs.jsc

import com.openland.mentaljs.jsc.modules.SandboxModule
import com.openland.mentaljs.jsc.modules.BindingBenchmarkModule
import com.openland.mentaljs.jsc.modules.BindingBenchmarkModuleSpec
import com.openland.mentaljs.jsc.modules.SandboxModuleSpec
import com.openland.open.MentalNativeModule
import com.openland.open.ModuleSpec
import com.openland.open.OpenNativeApplication

class MainApplication : OpenNativeApplication() {

    override fun getModules(): Collection<Pair<MentalNativeModule, ModuleSpec>> {
        return listOf(
                SandboxModule() to SandboxModuleSpec,
                BindingBenchmarkModule() to BindingBenchmarkModuleSpec
        )
    }
}