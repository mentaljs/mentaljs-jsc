package com.openland.react

import com.fasterxml.jackson.core.TreeNode

abstract class Serializer {
    abstract fun parse(source: TreeNode): Any
}