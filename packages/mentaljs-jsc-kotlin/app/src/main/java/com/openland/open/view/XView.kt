package com.openland.open.view

import android.graphics.Color
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.openland.open.MentalRuntime

class XViewProps : ViewProps() {
    var width: Float? = null
    var height: Float? = null
    var maxWidth: Float? = null
    var flexGrow: Float = 0.0f
    var flexShrink: Float = 0.0f
    var flexBasis: Float? = null
    // var alignSelf: AsyncFlexAlignSelf? = null

    var marginBottom: Float? = null
    var marginTop: Float? = null
    var marginLeft: Float? = null
    var marginRight: Float? = null

    // var backgroundColor: Int? = null
    // var borderRadius: Float? = null
    // var backgroundPatch: AsyncPatch? = null
}

@LayoutSpec
object XViewSpec {

    @OnCreateLayout
    internal fun onCreateLayout(context: ComponentContext, @Prop spec: XViewProps, @Prop children: Array<ViewSpec>, @Prop runtime: MentalRuntime): Component {
        val res = Row.create(context)
        if (spec.width != null) {
            res.widthDip(spec.width!!)
        }
        if (spec.height != null) {
            res.heightDip(spec.height!!)
        }

        res.backgroundColor(Color.RED)

        for (c in children) {
            res.child(ViewResolver.resolveView(context, c.type, c.props, c.children, runtime))
        }

        return res.build()
    }
}