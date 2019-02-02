package com.openland.open

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

abstract class ModuleDescriptor {
    abstract fun getModuleMethods(): Map<String, ModuleMethodInvoker>
}