package com.openland.react

sealed class MethodArgument {
    object NullArgument : MethodArgument()
    object UndefinedArgument : MethodArgument()
    class StringArgument(val value: String) : MethodArgument()
    class NumberArgument(val value: Number) : MethodArgument()
    class BooleanArgument(val value: Boolean) : MethodArgument()
}

abstract class ModuleMethodInvoker {
    abstract fun invoke(source: Any, arguments: Array<MethodArgument>)
}