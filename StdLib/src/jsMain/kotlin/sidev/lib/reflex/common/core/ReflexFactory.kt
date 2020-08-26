package sidev.lib.reflex.common.core

import sidev.lib.reflex.common.native.SiNativeWrapper
import sidev.lib.reflex.js.*


internal actual val SiNativeWrapper.nativeInnerName: String? get()= when(val native= implementation){
    is JsCallable<*> -> native.innerName
    else -> jsNativeName(native)
}
internal actual val SiNativeWrapper.nativeFullName: String? get()= when(val native= implementation){
    is JsCallable<*> -> native.name
    else -> jsName(native) ?: "<halo-bro-do>"
}
//TODO <16 Agustus 2020> => untuk smtr sama dg [nativeFullName].
internal actual val SiNativeWrapper.nativeSimpleName: String? get()= nativeFullName