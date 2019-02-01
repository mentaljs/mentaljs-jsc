package com.openland.mentaljs.jsc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.openland.mentaljs.jsc.runtime.modules.BindingBenchmarkModule
import com.openland.mentaljs.jsc.runtime.modules.ConsoleModule
import com.openland.mentaljs.jsc.runtime.modules.EventEmitterModule
import com.openland.mentaljs.jsc.runtime.modules.TimerModule
import com.openland.mentaljs.jsc.runtime.v8.MentalRuntimeV8
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val runtime = MentalRuntimeV8()
    private val console = ConsoleModule()
    private val timer = TimerModule()
    private val eventEmitter = EventEmitterModule()
    private val benchmark = BindingBenchmarkModule()
    private val sandbox = SandboxModule()

    private fun empty() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val start = System.currentTimeMillis()
        for (i in 1..100000) {
            this.empty()
        }
        val d = System.currentTimeMillis() - start
        Log.d("MentalJS", "Java in $d ms" + ", " + (100000 * 1000.0 / d))

        runtime.registerNativeModule(console)
        runtime.registerNativeModule(timer)
        runtime.registerNativeModule(eventEmitter)
        runtime.registerNativeModule(benchmark)
        runtime.registerNativeModule(sandbox)
        runtime.start()
        runtime.start(loadData("polyfills.js"))
        runtime.start(loadData("index.js"))
        // runtime.start(loadData("console.js"))
        // runtime.start("console.log('hello', undefined, null)")
        // runtime.start(script)
        // runtime.start(loadData("setTimeoutBenchmark.js"))
        // runtime.start(loadData("bindingBenchmark.js"))
    }

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
