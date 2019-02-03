package com.openland.react

import com.fasterxml.jackson.core.TreeNode
import org.json.JSONObject

abstract class Serializer {
    abstract fun parse(source: TreeNode, handler: CallbackHandler): Any
}

interface CallbackHandler {
    fun invoke(key: String, args: JSONObject)
}

class ViewCallback(private val key: String, private val handler: CallbackHandler) {

    fun invoke(args: JSONObject) {
        handler.invoke(key, args)
    }
}