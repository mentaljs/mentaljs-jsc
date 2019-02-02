package com.openland.open.view

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.jr.ob.JSON
import com.fasterxml.jackson.jr.stree.JacksonJrsTreeCodec
import com.fasterxml.jackson.jr.stree.JrsArray
import com.fasterxml.jackson.jr.stree.JrsObject
import com.fasterxml.jackson.jr.stree.JrsString
import com.openland.open.MentalRuntime
import com.openland.open.Serializer
import kotlin.reflect.KClass

object ViewResolver {
    private val json = JSON.std.with(JacksonJrsTreeCodec())
    private val views = mutableMapOf<String, KClass<*>>()
    private val viewResolver = mutableMapOf<String, (ComponentContext, Any, Array<ViewSpec>, MentalRuntime) -> Component>()

    internal fun findProps(name: String): KClass<*> {
        return views[name]!!
    }

    internal fun resolveView(ctx: ComponentContext, name: String, props: Any, children: Array<ViewSpec>, runtime: MentalRuntime) =
            this.viewResolver[name]!!.invoke(ctx, props, children, runtime)

    fun registerView(name: String, props: KClass<*>, resolve: (ComponentContext, Any, Array<ViewSpec>, MentalRuntime) -> Component) {
        views[name] = props
        viewResolver[name] = resolve
    }

    fun parseViewSpec(src: String): ViewSpec {
        val node = json.treeFrom<TreeNode>(src)
        return this.parseViewSpec(node)
    }

    private fun parseViewSpec(node: TreeNode): ViewSpec {
        if (!node.isObject) {
            throw Error("")
        }
        val obj = node as JrsObject
        val key = (obj.get("key") as JrsString).value
        val type = (obj.get("type") as JrsString).value
        val children = (obj.get("children") as JrsArray)
        val propsSerializer = Class.forName(findProps(type).qualifiedName!! + "_Serializer").kotlin.objectInstance as Serializer
        val props = propsSerializer.parse(obj.get("props"))



        val ch = mutableListOf<ViewSpec>()
        for (i in 0 until children.size()) {
            ch.add(parseViewSpec(children.get(i)))
        }
        // val children =
        return ViewSpec(type, key, props, ch)
        // val children = (obj.get("children") as JrsArray)
    }
}
