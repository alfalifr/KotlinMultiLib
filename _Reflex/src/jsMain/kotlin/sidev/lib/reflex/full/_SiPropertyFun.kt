package sidev.lib.reflex.full

import sidev.lib.property.SI_UNINITIALIZED_VALUE
import sidev.lib.reflex.SiProperty1

//TODO <20 Agustus 2020> => Dg anggapan semua property dapat diakses dari objek pada Js.
internal actual fun <T, V> SiProperty1<T, V>.handleNativeForceGet(receiver: T, exceptionFromCommon: Throwable): V = when(exceptionFromCommon){
    is UninitializedPropertyAccessException -> SI_UNINITIALIZED_VALUE as V
    else -> throw exceptionFromCommon
}