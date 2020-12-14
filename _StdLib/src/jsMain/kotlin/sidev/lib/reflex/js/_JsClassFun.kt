package sidev.lib.reflex.js

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.reflex.fullName
import sidev.lib.reflex.native_.jsClass
import kotlin.reflect.KClass

fun <T: Any> JsClass<T>.toClassWrapper(): JsClass_<T> = if(isFunction) JsClassImpl_(this)
else throw IllegalArgumentException("Object: \"${this::class.fullName}\" bkn merupakan fungsi")

@Suppress(SuppressLiteral.UNCHECKED_CAST_TO_EXTERNAL_INTERFACE)
val <T: Any> JsClass_<T>.kotlin: KClass<T>
    get()= (jsPureFunction(this) as JsClass<T>).kotlin

@Suppress(SuppressLiteral.UNCHECKED_CAST)
val <T: Any> KClass<T>.jsClass: JsClass_<T>
    get()= (this as T).jsClass