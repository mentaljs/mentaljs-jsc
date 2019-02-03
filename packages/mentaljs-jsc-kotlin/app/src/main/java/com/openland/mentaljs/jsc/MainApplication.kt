package com.openland.mentaljs.jsc

import com.openland.mentaljs.jsc.modules.SandboxModule
import com.openland.mentaljs.jsc.modules.BindingBenchmarkModule
import com.openland.react.NativeModule
import com.openland.react.ReactApplication
import com.openland.react.ui.NativeViewFactory

class MainApplication : ReactApplication() {

    override fun getModules(): Collection<NativeModule> {
        return listOf(
                SandboxModule(),
                BindingBenchmarkModule()
        )
    }

    override fun getViewFactories(): Collection<NativeViewFactory<*>> {
        return emptyList()
    }
}