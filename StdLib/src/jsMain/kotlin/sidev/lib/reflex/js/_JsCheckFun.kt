package sidev.lib.reflex.js

import sidev.lib.exception.JsNullPointerException


fun jsNotNull(any: Any?, msg: String= ""){
    if(any == null || any.isUndefined)
        throw JsNullPointerException(msg)
}
/**
 * Bertujuan untuk mengubah apapun menjadi string.
 * Fungsi ini berguna bagi dynamic yg tidak diketahui tipenya di Kotlin, seperti `undefined`
 * sehingga terjadi error saat menjadikannya string di Js.
 */
fun str(any: dynamic): String = try { any.toString() } catch (e: Throwable){
    if(any == null) "null" else "undefined"
}

fun Any?.toString(): String = str(this)