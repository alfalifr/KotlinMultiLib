package sidev.lib.reflex.common.core

import sidev.lib.reflex.common.native.SiNativeWrapper
import sidev.lib.reflex.js.JsCallable
import sidev.lib.reflex.js.JsMutableProperty
import sidev.lib.reflex.js.jsName
import sidev.lib.reflex.js.jsNativeName


internal actual val SiNativeWrapper.nativeInnerName: String? get()= jsNativeName(implementation)
internal actual val SiNativeWrapper.nativeFullName: String? get()= when(val native= implementation){
    is JsCallable<*> -> native.name
    else -> jsName(native)
}
//TODO <16 Agustus 2020> => untuk smtr sama dg [nativeFullName].
internal actual val SiNativeWrapper.nativeSimpleName: String? get()= nativeFullName