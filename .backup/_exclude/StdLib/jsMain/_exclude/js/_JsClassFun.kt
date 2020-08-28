package sidev.lib.reflex.js

import sidev.lib.reflex.fullName

fun <T: Any> JsClass<T>.toClassWrapper(): JsClass_<T> = if(isFunction) JsClassImpl_(this)
else throw IllegalArgumentException("Object: \"${this::class.fullName}\" bkn merupakan fungsi")