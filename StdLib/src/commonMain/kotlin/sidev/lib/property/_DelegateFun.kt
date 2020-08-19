package sidev.lib.property

fun <R, T> mutableLazy(initializer: () -> T): MutableLazy<R, T> = MutableLazyImpl(initializer)