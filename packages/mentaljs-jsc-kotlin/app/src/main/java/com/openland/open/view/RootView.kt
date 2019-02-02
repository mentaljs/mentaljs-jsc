package com.openland.open.view

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.openland.open.MentalRuntime

@LayoutSpec
object RootViewSpec {
    @OnCreateLayout
    internal fun onCreateLayout(context: ComponentContext, @Prop spec: ViewSpec, @Prop runtime: MentalRuntime): Component {
        val res = Column.create(context)
        res.widthPercent(100.0f)
        res.heightPercent(100.0f)
        res.child(ViewResolver.resolveView(context, spec.type, spec.props, spec.children.toTypedArray(), runtime))
        return res.build()
    }
}