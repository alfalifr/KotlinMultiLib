package sidev.lib.reflex.js

import sidev.lib.check.notNullTo
import sidev.lib.reflex.fullName
import kotlin.js.Json
import kotlin.reflect.KType
import sidev.lib.reflex.js.call as _call
import sidev.lib.reflex.js.new as _new

/**
 * <11 Agustus 2020>
 * Container untuk menampung semua jenis object refleksi pada JS.
 * Container ini menganggap bahwa semua properti pada JS bisa diakses.
 * Untuk kasus properti, [parameters] memiliki size == 0.
 */
interface JsCallable<out T> {
    val name: String
    val parameters: List<JsParameter>
    fun call(vararg args: Any?): Any?
    fun callBy(args: Json): Any?
    fun new(vararg args: Any?): T
    fun newBy(args: Json): T
}



/*
internal class JsCallableImpl_Class<T: Any>(private val clazz: JsClass<T>): JsCallable<T>{
    override val parameters: List<JsParameter> = getParam(clazz)

    override fun call(vararg args: dynamic): Any? = _call(clazz, *args)
    override fun callBy(args: Json): Any? = call(*args.sliceWithParam(parameters).toTypedArray())

    override fun new(vararg args: dynamic): T = _new(clazz, *args) as T
    override fun newBy(args: Json): T = new(*args.sliceWithParam(parameters).toTypedArray())
}
 */

internal open class JsCallableImpl<out T>(protected open val func: Any) : JsCallable<T>{
    constructor(block: (args: Array<out Any?>) -> T): this(block as Any){
        isPureJsFunction= false
    }
    constructor(): this("any"){ //Untuk mengakomodasi JsPropertyImpl
        isPureJsFunction= false
    }

    private var isPureJsFunction: Boolean= true //hanya boleh diubah pada constructor.

    override val name: String = when{
        isPureJsFunction -> {
            try{ (func as JsClass<*>).name }
            catch (e: Throwable){ jsName(jsPureFunction(func)) }
        }
        else -> "<kotlin-lambda>"
    }
    override val parameters: List<JsParameter>
            = if(isPureJsFunction) getParam(jsPureFunction(func))
            else emptyList()

    override fun call(vararg args: Any?): Any?{
        validateArgs(*args)
        return if(isPureJsFunction) _call(func, *args)
        else (func as (args: Array<out Any?>) -> T)(args)
    }
    override fun callBy(args: Json): Any? = call(*args.sliceWithParam(parameters).toTypedArray())

    override fun new(vararg args: Any?): T {
        validateArgs(*args)
        return if(isPureJsFunction) _new(func, *args) as T
        else (func as (args: Array<out Any?>) -> T)(args)
    }
    override fun newBy(args: Json): T = new(*args.sliceWithParam(parameters).toTypedArray())
    override fun toString(): String = if(isPureJsFunction) func.toString() else "<kotlin-lambda>"

    private fun validateArgs(vararg processedArgs: Any?){
        if(processedArgs.size < parameters.size){
            for(i in processedArgs.size until parameters.size)
                if(parameters[i].isOptional.not())
                    throw IllegalArgumentException("""Fungsi: "$name" memiliki ${parameters.size} parameter, namun tersedia ${processedArgs.size} argumen yg tidak dapat memenuhi parameter wajib.""")
        }
    }
}