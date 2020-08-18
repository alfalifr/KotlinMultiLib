package sidev.lib.reflex.js.kotlin

import sidev.lib.reflex.js.JsValueOf
import sidev.lib.reflex.js.call
import sidev.lib.reflex.js.isFunction


//<14 Agustus 2020>
data class KotlinJsMetadata(val kind: String, val simpleName: String, val interfaces: List<dynamic>, val kClass: dynamic)

/**
 * Representasi fungsi Js berdasarkan konfigurasi Koltin.
 */
data class KotlinJsFunction(val func: Any, val metadata: KotlinJsMetadata): JsValueOf{
    init{
        if(func.isFunction.not())
            throw IllegalArgumentException("""Objek: "$func" bkn fungsi.""")
    }
    operator fun invoke(vararg args: Any?): dynamic = call(func, *args)
    fun new(vararg args: Any?): dynamic = sidev.lib.reflex.js.new(func, *args)
    override fun toString(): String = func.toString()
    override fun valueOf(): dynamic = func.asDynamic().valueOf()
}