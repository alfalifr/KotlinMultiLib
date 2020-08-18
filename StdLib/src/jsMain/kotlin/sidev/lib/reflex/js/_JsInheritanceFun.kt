package sidev.lib.reflex.js

import sidev.lib.reflex.js.kotlin.KotlinJsConst
import sidev.lib.reflex.js.kotlin.isKotlinFun
import sidev.lib.reflex.js.kotlin.kotlinSupertypes

fun getSupertypes(func: Any): List<JsCallable<*>>{
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