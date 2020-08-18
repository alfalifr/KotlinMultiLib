package sidev.lib.reflex.js

import sidev.lib.check.notNull
import sidev.lib.number.toNumber
import sidev.lib.reflex.common.core.ReflexConst
import sidev.lib.text.isDigit

/*
fun getParam(func: dynamic): List<JsParameter>{
    if(!(func as Any?).isFunction)
        return emptyList()

    return getParamName(func).mapIndexed { index, s -> object : JsParameter{
        override val index: Int = index
        override val name: String? = s
    } }
}
 */
/** Mengambil property `name`. Jika tidak ada, mengambil tipe dari object `this`. */
fun jsName(any: dynamic): String = js("any.name") as? String ?: any::class.js.name

fun jsNativeName(any: dynamic): String =
    try{ js("any.name") as? String ?: any::class.js.asDynamic().name }
    catch (e: Throwable){ ReflexConst.TEMPLATE_NO_NAME }