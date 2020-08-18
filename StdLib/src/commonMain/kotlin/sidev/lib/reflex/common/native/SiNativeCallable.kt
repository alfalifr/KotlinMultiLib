package sidev.lib.reflex.common.native

import sidev.lib.reflex.common.SiReflexImpl


interface SiNativeCallable<out R>: SiNative {
    val parameters: List<SiNativeParameter>
    fun call(vararg args: Any?): R
}

//internal abstract class SiNativeCallableImpl<out R>: SiReflexImpl(), SiNativeCallable<R>