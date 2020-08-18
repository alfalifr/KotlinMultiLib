@file:JvmName("SiNativeFunKtJvm")

package sidev.lib.reflex.common.native

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.common.*
import kotlin.reflect.*

/*
internal actual fun <T> getReturnType(siNativeCallable: SiNativeCallable<T>): SiType = when(val native= siNativeCallable.implementation){
    is KCallable<*> -> native.returnType.si
    else -> throw ReflexComponentExc(currentReflexedUnit = siNativeCallable::class, detMsg = "siNativeCallable bkn callable.")
}
 */