package sidev.lib.reflex.js

import sidev.lib.console.prine
import kotlin.js.Json
import sidev.lib.reflex.js.call as _call
import sidev.lib.reflex.js.new as _new

/**
 * Interface yg menandakan bahwa sebuah [JsCallable] merupakan konstruktor.
 * Interface ini dibedakan karena fungsi [call] dan [callBy] juga memanggil
 * [new] dan [newBy] agar mengembalikan instance dari `this`.
 */
interface JsConstructor<out T>: JsCallable<T>{
    override fun call(vararg args: Any?): Any? = new(*args)
    override fun callBy(args: Json): Any? = newBy(args)
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

internal open class JsConstructorImpl<out T>(override val func: Any) : JsCallableImpl<T>(func), JsConstructor<T>{
    constructor(block: (args: Array<out Any?>) -> T): this(block as Any){
        isPureJsFunction= false
    }
    override fun call(vararg args: Any?): Any? = super.new(*args)
    override fun callBy(args: Json): Any? = super.newBy(args)
}