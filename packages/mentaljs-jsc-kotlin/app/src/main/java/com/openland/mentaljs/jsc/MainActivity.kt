package com.openland.mentaljs.jsc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.openland.mentaljs.jsc.runtime.modules.ConsoleModule
import com.openland.mentaljs.jsc.runtime.modules.TimerModule
import com.openland.mentaljs.jsc.runtime.v8.MentalRuntimeV8

class MainActivity : AppCompatActivity() {

    val runtime = MentalRuntimeV8()
    val console = ConsoleModule()
    val timer = TimerModule()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runtime.registerNativeModule(console)
        runtime.registerNativeModule(timer)
        runtime.start()

        val script = """
            JSModules.JSTimer = {
                runCallbacks: function () {
                    NativeModules.Console.log('hello!' + Date.now());
                }
            }
            NativeModules.Console.log('Start' + Date.now());
            NativeModules.Timer.setTimeout(1, 10);
        """.trimIndent()
        runtime.start(script)
    }
}
