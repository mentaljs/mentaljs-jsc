package com.openland.react.ui

import com.openland.react.Serializer

class NativeViewRepository {

    private val registrations = mutableMapOf<String, NativeViewFactory<Any>>()
    private val propsSerializers = mutableMapOf<String, Serializer>()

    fun registerViewFactory(factory: NativeViewFactory<*>) {
        this.registrations[factory.name] = factory as NativeViewFactory<Any>
        this.propsSerializers[factory.name] = Class.forName(factory.propsClass.qualifiedName!! + "_Serializer").newInstance() as Serializer
    }

    fun findPropsSerializer(name: String): Serializer {
        return this.propsSerializers[name]!!
    }

    fun findViewFactory(name: String): NativeViewFactory<Any> {
        return this.registrations[name]!!
    }
}