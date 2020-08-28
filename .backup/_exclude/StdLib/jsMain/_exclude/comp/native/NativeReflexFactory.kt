package sidev.lib.reflex.comp.native

/*
import sidev.lib.reflex.js.*
import sidev.lib.reflex.js.kotlin.isKotlinFun
import sidev.lib.reflex.js.kotlin.kotlinMetadata


internal actual val SiNative.nativeFullName: String? get()= when(val native= implementation) {
    is JsCallable<*> -> native.name
    is JsParameter -> native.name
    else -> try{ jsName(native) } catch (e: Throwable){ "Objek: \"${str(native)}\" tidak punya property name." }
}
internal actual val SiNative.nativeSimpleName: String? get()= when(val native= implementation){
    is JsCallable<*> -> if(native.isKotlinFun) native.kotlinMetadata.simpleName else nativeFullName
    else -> nativeFullName
}
 */