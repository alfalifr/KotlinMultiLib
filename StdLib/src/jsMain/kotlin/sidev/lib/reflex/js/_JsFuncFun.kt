package sidev.lib.reflex.js

import sidev.lib.console.prine
import sidev.lib.reflex.js.kotlin.KotlinJsConst

/**
 * Mengambil fungsi yg didklarasikan pada konstruktor dari [any]
 * atau [any] itu sendiri jika merupakan fungsi.
 * Fungsi yg diambil hanya yg publik.
 */
fun getDeclaredFunction(any: Any): List<JsCallable<*>>{
    val any= try{ jsPureFunction(any) } catch (e: Throwable){ any } as Any
            //Agar dapat mengekstrak nilai sesungguhnya
            // jika [obj] merupakan wrapper seperti JsCallable.
    if(any.isUndefined)
        throw IllegalArgumentException("obj: undefined.") //Agar lebih kontekstual.
    return any.prototype.properties.filter { it.second?.isFunction == true }
        .map { prop ->
            var funName= jsName(jsPureFunction(prop.second!!))
            var func= prop.second!!
            if(funName.isBlank())
                JsReflexConst.FUNCTION_PATTERN.toRegex().findAll(prop.second!!.toString())
                    .forEach { res ->
                        val vals= res.groupValues
                        funName= prop.first

                        //Jika terdapat bbrp fungsi dg nama inner yg diberi Kotlin.
                        KotlinJsConst.FUNCTION_INNER_NAME_PATTERN.toRegex().findAll(prop.first).forEach {
                            funName= it.groupValues.last()
                        }

                        val paramStr= vals[2]
                        val blockStr= vals[3]
                        val newNamedFunStr= "function $funName($paramStr) {$blockStr}"
                        val newFun= eval("newFun = $newNamedFunStr")
                        func= newFun
                        setProperty(any.prototype, prop.first, func)
                    }
            object : JsCallableImpl<Any?>(func){
                override val innerName: String = prop.first
            }
        }.toList()
}

//val Any.constructor: Any