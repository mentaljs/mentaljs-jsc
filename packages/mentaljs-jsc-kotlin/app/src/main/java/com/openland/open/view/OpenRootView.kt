package com.openland.open.view

import android.content.Context
import android.widget.FrameLayout
import com.facebook.litho.LithoView
import com.openland.mentaljs.jsc.modules.MentalRuntime
import com.openland.mentaljs.jsc.modules.getNativeModule
import com.openland.open.OpenNativeApplication
import com.openland.open.modules.UIManager

class OpenRootView(val name: String, context: Context) : FrameLayout(context) {
    private val runtime: MentalRuntime = (context.applicationContext as OpenNativeApplication).runtime
    private val lithoView = LithoView(context)
    private var viewId = 0;

    init {
        this.addView(lithoView,
                android.widget.FrameLayout.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.viewId = this.runtime.getNativeModule<UIManager>()
                .attachRootView(this.name, this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.runtime.getNativeModule<UIManager>()
                .detachRootView(this.viewId)
    }
}