package com.openland.open

import android.app.Application
import com.facebook.soloader.SoLoader
import com.openland.open.engine.MentalRuntimeV8
import com.openland.open.modules.*
import java.io.IOException

abstract class OpenNativeApplication : Application() {

    lateinit var runtime: MentalRuntime

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        this.runtime = MentalRuntimeV8()

        // Load Modules
        this.runtime.registerNativeModule(ConsoleModule(), ConsoleModuleSpec)
        this.runtime.registerNativeModule(TimerModule(), TimerModuleSpec)
        this.runtime.registerNativeModule(EventEmitterModule(), EventEmitterModuleSpec)
        this.runtime.registerNativeModule(UIManager(), UIManagerSpec)
        for (m in this.getModules()) {
            this.runtime.registerNativeModule(m.first, m.second)
        }

        // Start VM
        this.runtime.start()
        this.runtime.start(loadData("polyfills.js"))
        this.runtime.start(loadData("index.js"))
        this.runtime.started()
    }

    abstract fun getModules(): Collection<Pair<MentalNativeModule, ModuleSpec>>

    private fun loadData(inFile: String): String {
        var tContents = ""

        try {
            val stream = assets.open(inFile)

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
            // Handle exceptions here
        }

        return tContents
    }
}