package sidev.lib.structure.data.value

interface LiveNullableVar<T>: LiveVar<T?>, NullableVar<T>

internal open class LiveNullableVarImpl<T>(v: T? = null): LiveVarImpl<T?>(v), LiveNullableVar<T>