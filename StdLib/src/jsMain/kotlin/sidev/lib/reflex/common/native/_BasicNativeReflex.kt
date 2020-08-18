package sidev.lib.reflex.common.native

import sidev.lib.reflex.common.SiParameter
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.common.core.ReflexTemplate
import sidev.lib.reflex.js.*
import sidev.lib.reflex.js.kotlin.jsConstructor
import kotlin.reflect.KClass

private val Any.jsClass get()= (if(this is KClass<*>) this else this::class).js

/** `this.extension` dapat berupa apa saja. */
internal actual fun getNativeClass(any: Any): Any = any.jsClass

/** `this.extension` yg dimaksud adalah native class. */
internal actual fun getNativeFunctions(nativeClass: Any): Sequence<Any>
        = getDeclaredFunction(nativeClass).asSequence()

/** Termasuk yg mutable. */
//TODO <16 Agustus 2020> => untuk smtr sama dg yg mutable karena Kotlin Js hanya menkonfigurasi mutable saja.
internal actual fun getNativeProperties(nativeClass: Any): Sequence<Any>
        = getNativeMutableProperties(nativeClass)

/** Tidak termasuk property yg immutable. */
internal actual fun getNativeMutableProperties(nativeClass: Any): Sequence<Any>
        = getDeclaredProperty(nativeClass.jsClass).asSequence()

/**
 * Mengambil member yg dapat dipanggil dan dijadikan sbg [SiNativeCallable].
 * Member yg diambil termasuk fungsi dan semua properti.
 */
internal actual fun getNativeMembers(nativeClass: Any): Sequence<Any>
        = getNativeMutableProperties(nativeClass) + getNativeFunctions(nativeClass)

//TODO <16 Agustus 2020> => untuk smtr konstruktor hanya bisa didapatkan 1.
internal actual fun getNativeConstructors(nativeClass: Any): Sequence<Any> = object : Sequence<JsCallable<Any>>{
    override fun iterator(): Iterator<JsCallable<Any>> = object : Iterator<JsCallable<Any>>{
        var index= 0
        override fun hasNext(): Boolean = index++ < 1
        override fun next(): JsCallable<Any> = JsCallableImpl(jsConstructor(jsClass) as Any)
    }
}


/** Biasanya `this` merupakan fungsi yg punya paramter. */
internal actual fun getNativeParameters(nativeFunc: Any): Sequence<Any>
        = getParam(nativeFunc.jsClass).asSequence()

/** Mengambil immediate supertype dari `this`. */
internal actual fun getNativeSupertypes(nativeClass: Any): Sequence<Any>
        = getSupertypes(nativeClass.jsClass).asSequence()


internal actual fun getParamIsOptional(nativeParam: Any): Boolean = (nativeParam as JsParameter).isOptional
internal actual fun getParamType(nativeParam: Any): SiType = ReflexTemplate.typeDynamic
internal actual fun getParamKind(nativeParam: Any): SiParameter.Kind = SiParameter.Kind.VALUE
internal actual fun <T> getFuncCallBlock(nativeFuncHost: Any, nativeFunc: Any): (args: Array<out Any?>) -> T
        = { (nativeFunc as JsCallable<T>).call(*it) as T }

internal actual fun <T> getPropGetValueBlock(nativeProp: Any): (receivers: Array<out Any>) -> T = {
    (nativeProp as JsProperty<Any, T>).get(it.first())
}
internal actual fun <T> getPropSetValueBlock(nativeProp: Any): (receivers: Array<out Any>, value: T) -> Unit
        = { receivers, value ->
    (nativeProp as JsMutableProperty<Any, T>)[receivers.first()]= value
}

internal actual fun getReturnType(nativeCallable: Any): SiType = ReflexTemplate.typeDynamic