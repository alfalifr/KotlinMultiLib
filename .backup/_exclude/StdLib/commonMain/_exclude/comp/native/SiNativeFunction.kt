package sidev.lib.reflex.comp.native


interface SiNativeFunction<out R>: SiNativeCallable<R>

internal abstract class SiNativeFunctionImpl<out R>(callBlock: (args: Array<out Any?>) -> R)
    : SiNativeCallableImpl<R>(callBlock), SiNativeFunction<R>