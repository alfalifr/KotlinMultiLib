package sidev.lib.reflex.common.native

import sidev.lib.console.prine
import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.common.SiParameter
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.common.core.ReflexTemplate
import sidev.lib.reflex.js.*
import sidev.lib.reflex.js.kotlin.KotlinJsConst
import sidev.lib.universal.`val`.SuppressLiteral
import kotlin.reflect.KClass
import sidev.lib.reflex.js.isFunction


internal actual val isDynamicEnabled: Boolean = true

@Suppress(SuppressLiteral.UNCHECKED_CAST)
private val <T: Any> T.jsClass: JsClass_<T> get(){
    if(this is JsClass_<*>) return this as JsClass_<T>

    @Suppress(SuppressLiteral.UNCHECKED_CAST_TO_EXTERNAL_INTERFACE)
    val jsClass= (if(this is KClass<*>) this else this::class).js as JsClass<T>
//    prine("val jsClass jsClass= ${str(jsClass)}")

    return if(jsClass.name.startsWith(KotlinJsConst.K_FUNCTION_NAME_PREFIX)){
        //Jika jsClass berupa fungsi dg nama prefix Function, artinya [jsClass] merupakan function yg didefinisikan
        // menggunakan js() atau eval(), maka kembalikan fungsi itu sendiri, namun dibungkus [JsClass_],
        // walau tidak memiliki metadata seperti yg disuntikan pada Kotlin.
        if(isFunction)
            JsClassImpl_(this)
        else throw ReflexComponentExc(currentReflexedUnit = this, detMsg = "`this` bkn merupakan fungsi Js.")
    }
    else jsClass.toClassWrapper()
}

/** `this.extension` dapat berupa apa saja. */
internal actual fun getNativeClass(any: Any): Any = any.jsClass

/** `this.extension` yg dimaksud adalah native class. */
internal actual fun getNativeFunctions(nativeClass: Any): Sequence<Any>
        = getDeclaredFunction(nativeClass.jsClass).asSequence()

/** Termasuk yg mutable. */
//TODO <16 Agustus 2020> => untuk smtr sama dg yg mutable karena Kotlin Js hanya menkonfigurasi mutable saja.
//TODO <19 Agustus 2020> => untuk smtr immutable empty.
internal actual fun getNativeProperties(nativeClass: Any): Sequence<Any> = emptySequence()

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
        override fun next(): JsCallable<Any> = JsCallableImpl(jsConstructor(nativeClass.jsClass) as Any)
    }
}


/** Biasanya `this` merupakan fungsi yg punya paramter. */
internal actual fun getNativeParameters(nativeFunc: Any): Sequence<Any>
        = getParam((jsPureFunction(nativeFunc) as Any).jsClass).asSequence()

/** Mengambil immediate supertype dari `this`. */
internal actual fun getNativeSupertypes(nativeClass: Any): Sequence<Any>
        = getSupertypes(nativeClass.jsClass).asSequence()


internal actual fun getParamIsOptional(nativeParam: Any): Boolean = (nativeParam as JsParameter).isOptional
internal actual fun getParamType(nativeParam: Any): SiType = ReflexTemplate.typeDynamic
internal actual fun getParamKind(nativeParam: Any): SiParameter.Kind = SiParameter.Kind.VALUE
internal actual fun getParamDefaultValue(nativeParam: Any): Any? = (nativeParam as JsParameter).defaultValue
internal actual fun <T> getFuncCallBlock(nativeFuncHost: Any, nativeFunc: Any): (args: Array<out Any?>) -> T
        = { (nativeFunc as JsCallable<T>).call(*it) as T }

internal actual fun <T> getPropGetValueBlock(nativeProp: Any): (receivers: Array<out Any>) -> T = {
    (nativeProp as JsProperty<Any, T>)[it.first()]
}
internal actual fun <T> getPropSetValueBlock(nativeProp: Any): (receivers: Array<out Any>, value: T) -> Unit
        = { receivers, value ->
    (nativeProp as JsMutableProperty<Any, T>)[receivers.first()]= value
}

internal actual fun getReturnType(nativeCallable: Any): SiType = ReflexTemplate.typeDynamic