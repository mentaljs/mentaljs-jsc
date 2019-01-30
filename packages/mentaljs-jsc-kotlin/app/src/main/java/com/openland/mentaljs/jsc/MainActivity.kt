package com.openland.mentaljs.jsc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.Releasable
import com.eclipsesource.v8.JavaVoidCallback
import java.lang.System.console
import com.eclipsesource.v8.V8Object

//import org.liquidplayer.javascript.JSContext
//import org.liquidplayer.javascript.JSFunction


internal class Console {
    fun log(message: String) {
        // println("[INFO] $message")
    }

//    fun error(message: String) {
//        // println("[ERROR] $message")
//    }
}


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val runtime = V8.createV8Runtime()
        val console = Console()
        val callback = JavaVoidCallback { receiver, parameters ->
            if (parameters.length() > 0) {
                val arg1 = parameters.get(0)
//                println(arg1)
//                if (arg1 is Releasable) {
//                    arg1.release()
//                }
            }
        }
        val v8Console = V8Object(runtime)
        runtime.add("console", v8Console)
        // runtime.add("print", callback)
        v8Console.registerJavaMethod(console, "log", "log", arrayOf<Class<*>>(String::class.java))
//        v8Console.registerJavaMethod(console, "err", "err", arrayOf<Class<*>>(String::class.java))
        // runtime.registerJavaMethod(callback, "print")
        val start = System.currentTimeMillis()
        runtime.executeVoidScript("function a() {}; for (var i = 0; i<100000;i++) { console.log('Hello') }")
        val end = System.currentTimeMillis() - start
        Log.d("Benchmark", "Result: $end ms")
        Log.d("Benchmark", "Result: ${100000.0f * 1000.0f / end} op/sec");


//        val context = JSContext()
//        val print = object : JSFunction(context, "print") {
//            fun print(x: String) {
////                var x = x
////                var factorial = 1
////                while (x > 1) {
////                    factorial *= x!!
////                    x--
////                }
////                return factorial
//            }
//        }
//        context.property("print", print)
//        val start = System.currentTimeMillis()
//        context.evaluateScript("function a() {}; for (var i = 0; i<10000;i++) { print('Hello') }")
//        val end = System.currentTimeMillis() - start
//        Log.d("Benchmark", "Result: $end ms")
//        Log.d("Benchmark", "Result: ${10000.0f * 1000.0f / end} op/sec");
    }
}
