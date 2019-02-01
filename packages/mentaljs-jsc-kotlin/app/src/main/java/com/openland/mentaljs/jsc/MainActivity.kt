package com.openland.mentaljs.jsc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.openland.mentaljs.jsc.runtime.modules.BindingBenchmarkModule
import com.openland.mentaljs.jsc.runtime.modules.ConsoleModule
import com.openland.mentaljs.jsc.runtime.modules.EventEmitterModule
import com.openland.mentaljs.jsc.runtime.modules.TimerModule
import com.openland.mentaljs.jsc.runtime.v8.MentalRuntimeV8
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val runtime = MentalRuntimeV8()
    val console = ConsoleModule()
    val timer = TimerModule()
    val eventEmitter = EventEmitterModule()
    val benchmark = BindingBenchmarkModule()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runtime.registerNativeModule(console)
        runtime.registerNativeModule(timer)
        runtime.registerNativeModule(eventEmitter)
        runtime.registerNativeModule(benchmark)
        runtime.start()

        val script = """
            var console = {
                log: NativeModules.Console.log,
            }
            // var start = Date.now();


            // console.log('Benchmark 1: ' + (Date.now() - start) + ' ms');
            // NativeModules.Console.log('Start' + Date.now());
            // setTimeout(function () { NativeModules.Console.log('hello!' + Date.now()); }, 10);
            // var count = 0
            // var d = setInterval(function () { console.log('hello'); count++; if (count > 10) { clearTimeout(d); } }, 1000);
        """.trimIndent()
        runtime.start(loadData("core.js"))
        runtime.start(script)
        // runtime.start(loadData("setTimeoutBenchmark.js"))
        runtime.start(loadData("bindingBenchmark.js"))
    }

    fun loadData(inFile: String): String {
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
