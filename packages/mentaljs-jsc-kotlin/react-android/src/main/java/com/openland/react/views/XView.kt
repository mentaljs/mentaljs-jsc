package com.openland.react.views

import com.facebook.litho.ClickEvent
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.annotations.*
import com.openland.react.MentalProps
import com.openland.react.ReactContext
import com.openland.react.ViewCallback
import com.openland.react.calculateComponent
import com.openland.react.ui.NativeViewFactory
import com.openland.react.ui.ViewSpec
import org.json.JSONObject

class XViewFactory : NativeViewFactory<XViewProps>("XView", XViewProps::class) {
    override fun createView(context: ComponentContext, props: XViewProps, children: Array<ViewSpec>, ctx: ReactContext): Component {
        return XView.create(context)
                .spec(props)
                .children(children)
                .reactContext(ctx)
                .build()
    }
}

@MentalProps
class XViewProps {
    var width: Float? = null
    var height: Float? = null
    var maxWidth: Float? = null
    var minWidth: Float? = null
    var maxHeight: Float? = null
    var minHeight: Float? = null

    var flexGrow: Float = 0.0f
    var flexShrink: Float = 0.0f
    var flexBasis: Float? = null
    // var alignSelf: AsyncFlexAlignSelf? = null

    var marginBottom: Float? = null
    var marginTop: Float? = null
    var marginLeft: Float? = null
    var marginRight: Float? = null

    var backgroundColor: Int? = null

    var onPress: ViewCallback? = null
}

@LayoutSpec
object XViewSpec {

    @OnCreateLayout
    internal fun onCreateLayout(context: ComponentContext, @Prop spec: XViewProps, @Prop children: Array<ViewSpec>, @Prop reactContext: ReactContext): Component {
        val res = Row.create(context)
        if (spec.width != null) {
            res.widthDip(spec.width!!)
        }
        if (spec.height != null) {
            res.heightDip(spec.height!!)
        }

        if (spec.backgroundColor != null) {
            res.backgroundColor(spec.backgroundColor!!)
        }

        if (spec.onPress != null) {
            res.clickHandler(XView.onClick(context))
        }

        for (c in children) {
            res.child(calculateComponent(context, c, reactContext))
        }

        return res.build()
    }

    @OnEvent(ClickEvent::class)
    @JvmName("onClick")
    internal fun onClick(context: ComponentContext, @Prop spec: XViewProps, @Prop children: Array<ViewSpec>, @Prop reactContext: ReactContext) {
        spec.onPress?.invoke(JSONObject())
    }
}