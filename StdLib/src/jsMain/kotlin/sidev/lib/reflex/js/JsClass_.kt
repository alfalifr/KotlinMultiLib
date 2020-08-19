package sidev.lib.reflex.js

import sidev.lib.universal.`val`.SuppressLiteral

/**
 * Wrapper [JsClass] agar dapat dilakukan pengecekan `is` pada runtime Kotlin.
 */
interface JsClass_<T: Any> : JsCallable<T>

internal class JsClassImpl_<T: Any>(func: Any): JsCallableImpl<T>(func), JsClass_<T>
/*
{
    @Suppress(SuppressLiteral.UNCHECKED_CAST_TO_EXTERNAL_INTERFACE)
    override val name: String = try{ (func as JsClass<*>).name } catch (e: Throwable){ super.name }
    override val prototype: Any get() = func.prototype
    override fun toString(): String = func.toString()
    override fun valueOf(): dynamic = func.asDynamic().valueOf()
}
 */