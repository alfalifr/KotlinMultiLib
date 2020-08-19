package sidev.lib.reflex.js

import sidev.lib.check.notNullTo
import sidev.lib.exception.ImplementationExc
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.common.SiClass
import sidev.lib.reflex.fullName
import sidev.lib.reflex.js.kotlin.KotlinJsFunction
import kotlin.js.Json


internal fun jsPureFunction(func: Any): dynamic = when(func){
    is KotlinJsFunction -> func.func
    is JsCallable<*> -> try { jsPureFunction((func as JsCallableImpl<*>).func) } //Recursive karena bisa jadi func-nya juga merupakan callable lainnya.
        catch (e: Throwable){ throw ImplementationExc(implementedClass = JsCallable::class) }
    is SiClass<*> -> jsPureFunction(func.descriptor.native!!) //Recursive karena native-nya berupa [JsClass_].
    else -> {
        if(!func.isFunction)
            throw IllegalArgumentException("""Objek: "${str(func)}" bkn fungsi.""")
        func
    }
}

fun call(func: Any, vararg args: Any?, default: Any?= undefined): Any?
        = try{ jsPureFunction(func).apply(null, args) } catch (e: Throwable){ default }

fun new(func: Any, vararg args: Any?, default: Any?= undefined): Any?
        = try{ js("Reflect").construct(jsPureFunction(func), args) } catch (e: Throwable){ default }


fun Json.sliceWithParam(params: Collection<JsParameter>): List<Any?>{
    return toList().mapNotNull { jsObj ->
        params.find{ it.name == jsObj.first }.notNullTo {
            Pair(it.index, jsObj.second)
        }
    }.sortedBy { it.first }
        .map { it.second }
}

//fun <T: Any> JsClass<T>.toCallable(): JsCallable<T> = JsCallableImpl_Class(this)
fun <T: Any> T.toCallable(): JsCallable<T> = if(isFunction) JsCallableImpl(this)
else throw IllegalArgumentException("Object: \"${this::class.fullName}\" bkn merupakan fungsi")

fun <T: Any> JsClass<T>.toCallable(): JsCallable<T> = (this as Any).toCallable() as JsCallable<T>