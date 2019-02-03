package com.openland.react.ui

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.openland.react.ReactContext
import com.openland.react.calculateComponent

@LayoutSpec
object RootViewSpec {
    @OnCreateLayout
    internal fun onCreateLayout(context: ComponentContext, @Prop spec: ViewSpec, @Prop reactContext: ReactContext): Component {
        val res = Column.create(context)
        res.widthPercent(100.0f)
        res.heightPercent(100.0f)
        res.child(calculateComponent(context, spec, reactContext))
        return res.build()
    }
}