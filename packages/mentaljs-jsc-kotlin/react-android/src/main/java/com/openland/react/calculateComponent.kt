package com.openland.react

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.openland.react.ui.ViewSpec

fun calculateComponent(context: ComponentContext, spec: ViewSpec, reactContext: ReactContext): Component {
    return reactContext.uiManager.resolveView(context, spec)
}