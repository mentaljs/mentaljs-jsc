package com.openland.react.ui

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.openland.react.ReactContext
import kotlin.reflect.KClass

abstract class NativeViewFactory<P : Any>(val name: String, val propsClass: KClass<P>) {
    abstract fun createView(context: ComponentContext, key: String, props: P, children: Array<ViewSpec>, ctx: ReactContext): Component
}