package com.openland.open

import android.app.Application
import com.facebook.soloader.SoLoader
import com.openland.react.NativeModule
import com.openland.react.NativeModuleSpec
import com.openland.react.ReactContext

abstract class OpenNativeApplication : Application() {

    lateinit var reactContext: ReactContext

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        reactContext = ReactContext(this, this.getModules())
    }

    abstract fun getModules(): Collection<Pair<NativeModule, NativeModuleSpec>>
}