package com.openland.open.view

import com.beust.klaxon.TypeAdapter
import com.beust.klaxon.TypeFor
import kotlin.reflect.KClass

class ViewResolverAdapter : TypeAdapter<ViewProps> {
    override fun classFor(type: Any): KClass<out ViewProps> = ViewResolver.findProps(type as String)
}

open class ViewProps

class ViewSpec(
        @TypeFor(field = "props", adapter = ViewResolverAdapter::class)
        val type: String,
        val key: String,
        val props: ViewProps,
        val children: Array<ViewSpec>
)