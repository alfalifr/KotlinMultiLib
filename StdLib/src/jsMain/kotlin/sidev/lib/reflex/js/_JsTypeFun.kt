package sidev.lib.reflex.js

import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection

//<15 Agustus 2020> => Semua receiver non-null agar sesuai konteks.

val Any.isNumber: Boolean
    get()= jsTypeOf(this) == JsType.NUMBER.jsName

val Any.isString: Boolean
    get()= jsTypeOf(this) == JsType.STRING.jsName

val Any.isFunction: Boolean
    get()= jsTypeOf(this) == JsType.FUNCTION.jsName

val Any.isObject: Boolean
    get()= jsTypeOf(this) == JsType.OBJECT.jsName
/*
val Any?.isNull: Boolean
    get()= this == null
 */

val Any.isUndefined: Boolean
    get()= jsTypeOf(this) == JsType.UNDEFINED.jsName
