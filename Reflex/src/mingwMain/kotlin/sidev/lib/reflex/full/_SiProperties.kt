package sidev.lib.reflex.full

import sidev.lib.property.UNINITIALIZED_VALUE
import sidev.lib.reflex.*


internal actual fun <T, V> SiProperty1<T, V>.handleNativeForceGet(receiver: T, exceptionFromCommon: Throwable): V = when(exceptionFromCommon){
    is UninitializedPropertyAccessException -> UNINITIALIZED_VALUE as V
    else -> throw exceptionFromCommon
}