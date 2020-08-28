package sidev.lib.reflex.full

import sidev.lib.property.UNINITIALIZED_VALUE
import sidev.lib.reflex.comp.SiProperty1

//TODO <20 Agustus 2020> => Dg anggapan semua property dapat diakses dari objek pada Js.
actual fun <T, V> SiProperty1<T, V>.handleNativeForceGet(receiver: T, exceptionFromCommon: Throwable): V = when(exceptionFromCommon){
    is UninitializedPropertyAccessException -> UNINITIALIZED_VALUE as V
    else -> throw exceptionFromCommon
}