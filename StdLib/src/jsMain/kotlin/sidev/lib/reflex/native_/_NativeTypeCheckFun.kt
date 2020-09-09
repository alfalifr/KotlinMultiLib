package sidev.lib.reflex.native_

import sidev.lib.reflex.js.*
import sidev.lib.reflex.js.isFunction

internal actual val Any.isNativeClassifier: Boolean
    get()= isNativeClass
internal actual val Any.isNativeClass: Boolean
    get()= this is JsClass_<*> || isFunction //Pengecekan is JsClass tidak bisa karena external.
internal actual val Any.isNativeType: Boolean
    get()= isNativeClass
internal actual val Any.isNativeCallable: Boolean
    get()= this is JsCallable<*>
internal actual val Any.isNativeFunction: Boolean
    get()= isNativeCallable //Karena fungsi di js sangat simpel, jadinya sama dg callable biasa. //isFunction
internal actual val Any.isNativeProperty: Boolean
    get()= isNativeMutableProperty //TODO <16 Agusutus 2020> => Untuk smtr tidak ada immutable property //this is JsProperty<*, *>
internal actual val Any.isNativeMutableProperty: Boolean
    get()= this is JsMutableProperty<*, *>
internal actual val Any.isNativeParameter: Boolean
    get()= this is JsParameter