package com.openland.open

import com.fasterxml.jackson.core.TreeNode

sealed class MethodArgument {
    class NullArgument() : MethodArgument()
    class UndefinedArgument() : MethodArgument()
    class StringArgument(val value: String) : MethodArgument()
    class NumberArgument(val value: Number) : MethodArgument()
    class BooleanArgument(val value: Boolean) : MethodArgument()
}

abstract class ModuleMethodInvoker {
    abstract fun invoke(source: Any, arguments: Array<MethodArgument>)
}

abstract class Serializer {
    abstract fun parse(source: TreeNode): Any
}

abstract class ModuleSpec {
    abstract fun getModuleMethods(): Map<String, ModuleMethodInvoker>
}