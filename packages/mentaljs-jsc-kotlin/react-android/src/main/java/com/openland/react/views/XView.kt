package com.openland.react.views

import com.facebook.litho.*
import com.facebook.litho.annotations.*
import com.facebook.yoga.YogaEdge
import com.openland.react.MentalProps
import com.openland.react.ReactContext
import com.openland.react.ViewCallback
import com.openland.react.calculateComponent
import com.openland.react.ui.NativeViewFactory
import com.openland.react.ui.ViewSpec
import org.json.JSONObject
import com.facebook.litho.animation.AnimatedProperties
import com.facebook.litho.annotations.OnCreateTransition
import com.facebook.litho.annotations.OnUpdateState


class XViewFactory : NativeViewFactory<XViewProps>("XView", XViewProps::class) {
    override fun createView(context: ComponentContext, key: String, props: XViewProps, children: Array<ViewSpec>, ctx: ReactContext): Component {
        return XView.create(context)
                .key(key)
                .viewKey(key)
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

    var paddingBottom: Float? = null
    var paddingTop: Float? = null
    var paddingLeft: Float? = null
    var paddingRight: Float? = null

    var backgroundColor: Int? = null
    var opacity: Float? = null
    var animate: String? = null

    var onPress: ViewCallback? = null
}

@LayoutSpec
object XViewSpec {

    @OnCreateLayout
    internal fun onCreateLayout(context: ComponentContext, @Prop viewKey: String, @Prop spec: XViewProps, @Prop children: Array<ViewSpec>, @Prop reactContext: ReactContext): Component {
        val res = Row.create(context)

        // Size
        if (spec.width != null) {
            res.widthDip(spec.width!!)
        }
        if (spec.minWidth != null) {
            res.minWidthDip(spec.minWidth!!)
        }
        if (spec.maxWidth != null) {
            res.maxWidthDip(spec.maxWidth!!)
        }

        if (spec.height != null) {
            res.heightDip(spec.height!!)
        }
        if (spec.minHeight != null) {
            res.minHeightDip(spec.minHeight!!)
        }
        if (spec.maxHeight != null) {
            res.maxHeightDip(spec.maxHeight!!)
        }

        // Flex
        res.flexGrow(spec.flexGrow)
        res.flexShrink(spec.flexShrink)
        if (spec.flexBasis != null) {
            res.flexBasisDip(spec.flexBasis!!)
        }

        // Margin
        if (spec.marginTop != null) {
            res.marginDip(YogaEdge.TOP, spec.marginTop!!)
        }
        if (spec.marginBottom != null) {
            res.marginDip(YogaEdge.BOTTOM, spec.marginBottom!!)
        }
        if (spec.marginLeft != null) {
            res.marginDip(YogaEdge.LEFT, spec.marginLeft!!)
        }
        if (spec.marginRight != null) {
            res.marginDip(YogaEdge.RIGHT, spec.marginRight!!)
        }

        // Padding
        if (spec.paddingTop != null) {
            res.paddingDip(YogaEdge.TOP, spec.paddingTop!!)
        }
        if (spec.paddingBottom != null) {
            res.paddingDip(YogaEdge.BOTTOM, spec.paddingBottom!!)
        }
        if (spec.paddingLeft != null) {
            res.paddingDip(YogaEdge.LEFT, spec.paddingLeft!!)
        }
        if (spec.paddingRight != null) {
            res.paddingDip(YogaEdge.RIGHT, spec.paddingRight!!)
        }

        // Styles
        if (spec.backgroundColor != null) {
            res.backgroundColor(spec.backgroundColor!!)
        }
        if (spec.opacity != null) {
            res.alpha(spec.opacity!!)
        }

        // Callbacks
        if (spec.onPress != null) {
            res.clickHandler(XView.onClick(context))
        }


        // Children
        for (c in children) {
            res.child(calculateComponent(context, c, reactContext))
        }

        res.transitionKeyType(Transition.TransitionKeyType.GLOBAL)
        res.transitionKey("view:$viewKey")

        return res.build()
    }

    @OnEvent(ClickEvent::class)
    @JvmName("onClick")
    internal fun onClick(context: ComponentContext, @Prop spec: XViewProps) {
        spec.onPress?.invoke(JSONObject())
    }

    @OnCreateTransition
    internal fun onCreateTransition(c: ComponentContext, @Prop viewKey: String, @Prop spec: XViewProps): Transition {
        Transition.allLayout()
        val res = Transition.create(Transition.TransitionKeyType.GLOBAL, "view:$viewKey")
        if (spec.animate == "opacity" || spec.animate == "all") {
            res.animate(AnimatedProperties.ALPHA)
        }
        if (spec.animate == "x" || spec.animate == "all") {
            res.animate(AnimatedProperties.X)
        }
        if (spec.animate == "y" || spec.animate == "all") {
            res.animate(AnimatedProperties.Y)
        }
        if (spec.animate == "w" || spec.animate == "all") {
            res.animate(AnimatedProperties.WIDTH)
        }
        if (spec.animate == "h" || spec.animate == "all") {
            res.animate(AnimatedProperties.HEIGHT)
        }
        return res
    }
}