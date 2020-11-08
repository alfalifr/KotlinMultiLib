package sidev.lib.reflex.js

import sidev.lib.`val`.SuppressLiteral
import sidev.lib.reflex.js.kotlin.KotlinJsConst
import sidev.lib.reflex.js.kotlin.isKotlinFun
import sidev.lib.reflex.js.kotlin.kotlinSupertypes

/**
 * Mengambil supertype dari fungsi [func].
 * Fungsi ini membutuhkan satu param [func] sbg fungsi, bkn objek. Walau sebenarnya
 * supertype juga dapat diperoleh jika [func] merupakan objek, namun krg sesua konteks.
 */
fun getSupertypes(func: Any): List<JsCallable<*>>{
    @Suppress(SuppressLiteral.NAME_SHADOWING)
    val func= jsPureFunction(func) as Any
    if(!func.isFunction)
        throw IllegalArgumentException("func: \"${str(func)}\" bkn fungsi.") //Agar lebih kontekstual.
    val supertypes= ArrayList<JsCallable<*>>()
    if(func.isKotlinFun){
        kotlinSupertypes(func).forEach {
            val supert= JsCallableImpl<Any>(it as Any)
            supertypes.add(supert)
        }
    } else{
        val funStr= func.toString()
        KotlinJsConst.FUNCTION_CONTRUCTOR_SUPER_CALL.toRegex()
            .findAll(funStr).forEach { res ->
                val funcObj= eval(res.groupValues.last())
                val supert= JsCallableImpl<Any>(funcObj as Any)
                supertypes.add(supert)
            }
    }
    return supertypes
}