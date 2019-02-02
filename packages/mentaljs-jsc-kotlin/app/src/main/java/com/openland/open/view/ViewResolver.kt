package com.openland.open.view

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.openland.open.MentalRuntime
import kotlin.reflect.KClass

object ViewResolver {
    private val views = mutableMapOf<String, KClass<out ViewProps>>()
    private val viewResolver = mutableMapOf<String, (ComponentContext, ViewProps, Array<ViewSpec>, MentalRuntime) -> Component>()

    internal fun findProps(name: String): KClass<out ViewProps> {
        return views[name]!!
    }

    internal fun resolveView(ctx: ComponentContext, name: String, props: ViewProps, children: Array<ViewSpec>, runtime: MentalRuntime) =
            this.viewResolver[name]!!.invoke(ctx, props, children, runtime)

    fun <T : ViewProps> registerView(name: String, props: KClass<T>, resolve: (ComponentContext, ViewProps, Array<ViewSpec>, MentalRuntime) -> Component) {
        views[name] = props
        viewResolver[name] = resolve
    }
}
