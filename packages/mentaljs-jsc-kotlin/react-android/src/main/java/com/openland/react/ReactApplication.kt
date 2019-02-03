package com.openland.react

import android.app.Application
import com.facebook.soloader.SoLoader
import com.openland.react.ui.NativeViewFactory
import com.openland.react.views.XViewFactory

abstract class ReactApplication : Application() {

    lateinit var reactContext: ReactContext

    override fun onCreate() {
        super.onCreate()

        SoLoader.init(this, false)

        reactContext = ReactContext(this, this.getModules(), this.getViewFactories() + XViewFactory())
    }

    abstract fun getModules(): Collection<NativeModule>
    abstract fun getViewFactories(): Collection<NativeViewFactory<*>>
}