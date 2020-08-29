package sidev.lib.reflex.core

import sidev.lib.reflex.native.SiNative
import sidev.lib.reflex.js.*


internal actual val SiNative.nativeInnerName: String? get()= when(val native= implementation){
    is JsCallable<*> -> native.innerName
    else -> jsNativeName(native)
}
internal actual val SiNative.nativeFullName: String? get()= when(val native= implementation){
    is JsCallable<*> -> native.name
    else -> jsName(native) ?: "<halo-bro-do>"
}
//TODO <16 Agustus 2020> => untuk smtr sama dg [nativeFullName].
internal actual val SiNative.nativeSimpleName: String? get()= nativeFullName