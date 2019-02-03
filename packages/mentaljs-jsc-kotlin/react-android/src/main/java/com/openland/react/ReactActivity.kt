package com.openland.react

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class ReactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ReactRootView(getMainComponentName(), this))
    }

    abstract fun getMainComponentName(): String
}