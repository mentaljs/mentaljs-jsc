package com.openland.react

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.openland.react.runtime.AndroidV8Runtime
import com.openland.react.ui.NativeViewFactory
import com.openland.react.ui.UIManager
import java.io.IOException
import kotlin.reflect.KClass

object AndroidConsole : ConsoleHandler {
    override fun log(msg: String) {
        Log.i("ReactJS", msg)
    }

    override fun debug(msg: String) {
        Log.d("ReactJS", msg)
    }

    override fun error(msg: String) {
        Log.e("ReactJS", msg)
    }

    override fun warn(msg: String) {
        Log.w("ReactJS", msg)
    }
}

class AndroidTimer(looper: Looper) : TimerHandler {
    private var callback: ((IntArray) -> Unit)? = null
    private val handler = Handler(looper, Handler.Callback {
        callback!!(intArrayOf(it.what))
        true
    })

    override fun setTimerCallback(callback: (IntArray) -> Unit) {
        this.callback = callback
    }

    override fun setTimeout(id: Int, duration: Int) {
        handler.sendEmptyMessageDelayed(id, duration.toLong())
    }

    override fun clearTimeout(id: Int) {
        handler.removeMessages(id)
    }
}

class ReactContext(val context: Context, modules: Collection<NativeModule>, views: Collection<NativeViewFactory<*>>) {
    private val thread = HandlerThread("v8-runner")
    private val workerThread = HandlerThread("v8-worker")
    private val runtime: AndroidV8Runtime
    val uiManager: UIManager = UIManager(this, views)

    init {
        thread.start()
        workerThread.start()
        while (thread.looper == null || workerThread.looper == null) {
            Thread.sleep(1)
        }
        this.runtime = AndroidV8Runtime(thread.looper, workerThread.looper)

        // Modules
        this.runtime.registerNativeModule(ConsoleModule(AndroidConsole))
        this.runtime.registerNativeModule(TimerModule(AndroidTimer(thread.looper)))
        this.runtime.registerNativeModule(EventEmitterModule())
        this.runtime.registerNativeModule(uiManager)
        for (m in modules) {
            this.runtime.registerNativeModule(m)
        }

        // Start VM
        this.runtime.start()
        this.runtime.start(loadData("polyfills.js"))
        this.runtime.start(loadData("index.js"))
        this.runtime.started()
    }

    fun <T : NativeModule> getNativeModule(clazz: KClass<T>): T {
        return this.runtime.getNativeModule(clazz)
    }

    private fun loadData(inFile: String): String {
        var tContents = ""

        try {
            val stream = this.context.assets.open(inFile)

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