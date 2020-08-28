package sidev.lib.reflex.native

import sidev.lib.reflex.js.*
import sidev.lib.reflex.js.isFunction

internal actual val Any.isClassifier: Boolean
    get()= isClass
internal actual val Any.isClass: Boolean
    get()= this is JsClass_<*> || isFunction //Pengecekan is JsClass tidak bisa karena external.
internal actual val Any.isType: Boolean
    get()= isClass
internal actual val Any.isCallable: Boolean
    get()= this is JsCallable<*>
internal actual val Any.isFunction: Boolean
    get()= isCallable //Karena fungsi di js sangat simpel, jadinya sama dg callable biasa. //isFunction
internal actual val Any.isProperty: Boolean
    get()= isMutableProperty //TODO <16 Agusutus 2020> => Untuk smtr tidak ada immutable property //this is JsProperty<*, *>
internal actual val Any.isMutableProperty: Boolean
    get()= this is JsMutableProperty<*, *>
internal actual val Any.isParameter: Boolean
    get()= this is JsParameter