package com.openland.open

import android.app.Application
import com.facebook.soloader.SoLoader
import com.openland.open.modules.ConsoleModule
import com.openland.open.modules.EventEmitterModule
import com.openland.open.modules.TimerModule
import com.openland.open.modules.UIManager
import com.openland.open.engine.MentalRuntimeV8
import java.io.IOException

abstract class OpenNativeApplication : Application() {

    lateinit var runtime: MentalRuntime

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        this.runtime = MentalRuntimeV8()

        // Load Modules
        this.runtime.registerNativeModule(ConsoleModule())
        this.runtime.registerNativeModule(TimerModule())
        this.runtime.registerNativeModule(EventEmitterModule())
        this.runtime.registerNativeModule(UIManager())
        for (m in this.getModules()) {
            this.runtime.registerNativeModule(m)
        }

        // Start VM
        this.runtime.start()
        this.runtime.start(loadData("polyfills.js"))
        this.runtime.start(loadData("index.js"))
        this.runtime.started()
    }

    abstract fun getModules(): Collection<MentalNativeModule>

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