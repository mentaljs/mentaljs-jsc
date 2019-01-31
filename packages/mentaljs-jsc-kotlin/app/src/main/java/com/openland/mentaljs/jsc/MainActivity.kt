package com.openland.mentaljs.jsc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.openland.mentaljs.jsc.runtime.modules.ConsoleModule
import com.openland.mentaljs.jsc.runtime.modules.TimerModule
import com.openland.mentaljs.jsc.runtime.v8.MentalRuntimeV8

val timerJs = """
    var __handlers = {
    guid: 1,
    pendings: []
};
var setTimeout = function (handler, timeout) {
    var args = [];
    for (var _i = 2; _i < arguments.length; _i++) {
        args[_i - 2] = arguments[_i];
    }
    var id = __handlers.guid++;
    __handlers.pendings[id] = handler;
    NativeModules.Timer.setTimeout(id, timeout || 0);
};
var setInterval = function (handler, timeout) {
    var args = [];
    for (var _i = 2; _i < arguments.length; _i++) {
        args[_i - 2] = arguments[_i];
    }
    var id = __handlers.guid++;
    __handlers.pendings[id] = handler;
    NativeModules.Timer.setInterval(id, timeout || 0);
};
var clearTimeout = function (handle) {
    NativeModules.Timer.clearTimeout(handle);
};
var clearInterval = function (handle) {
    NativeModules.Timer.clearTimeout(handle);
};
JSModules.JSTimer = {
    runCallbacks: function (ids) {
        for (var _i = 0, ids_1 = ids; _i < ids_1.length; _i++) {
            var i = ids_1[_i];
            var p = __handlers.pendings[i];
            p();
        }
    }
};
""".trimIndent()

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
            NativeModules.Console.log('Start' + Date.now());
            setTimeout(function () { NativeModules.Console.log('hello!' + Date.now()); }, 10);
        """.trimIndent()
        runtime.start(timerJs)
        runtime.start(script)
    }
}
