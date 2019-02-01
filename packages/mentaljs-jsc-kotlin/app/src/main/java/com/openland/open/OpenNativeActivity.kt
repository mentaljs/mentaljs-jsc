package com.openland.open

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.openland.open.view.OpenRootView

abstract class OpenNativeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(OpenRootView(getMainComponentName(), this))
    }

    abstract fun getMainComponentName(): String
}