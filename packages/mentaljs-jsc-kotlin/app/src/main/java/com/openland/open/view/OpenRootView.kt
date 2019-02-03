package com.openland.open.view

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.widget.SolidColor
import com.openland.open.OpenNativeApplication
import com.openland.open.modules.UIManager
import com.openland.react.ReactContext

class OpenRootView(val name: String, context: Context) : FrameLayout(context) {
    private val reactContext: ReactContext = (context.applicationContext as OpenNativeApplication).reactContext
    private val asyncContext = ComponentContext(context)
    private val lithoView = LithoView(context)
    private var viewId = 0

    init {
        this.addView(lithoView,
                android.widget.FrameLayout.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT))
        lithoView.setComponent(SolidColor.create(asyncContext).color(Color.TRANSPARENT).build())
    }

    fun setConfig(config: ViewSpec) {
        this.lithoView.setComponentAsync(RootView.create(asyncContext)
                .spec(config)
                .reactContext(this.reactContext)
                .build())
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.viewId = this.reactContext.getNativeModule(UIManager::class)
                .attachRootView(this.name, this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.reactContext.getNativeModule(UIManager::class)
                .detachRootView(this.viewId)
    }
}