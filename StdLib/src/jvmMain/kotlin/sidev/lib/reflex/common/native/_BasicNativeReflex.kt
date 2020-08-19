@file:JvmName("BasicNativeReflexKtJvm")

package sidev.lib.reflex.common.native

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.common.SiParameter
import sidev.lib.reflex.common.SiType
import sidev.lib.reflex.common.SiTypeProjection
import sidev.lib.reflex.common.SiVariance
import sidev.lib.reflex.common.core.ReflexFactory
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import kotlin.reflect.*
import kotlin.reflect.full.*


internal actual val isDynamicEnabled: Boolean = false


/** `this.extension` dapat berupa apa saja. */
internal actual fun getNativeClass(any: Any): Any = if(any is KClass<*>) any else any::class

/** `this.extension` yg dimaksud adalah native class. */
internal actual fun getNativeFunctions(nativeClass: Any): Sequence<Any> =
    (getNativeClass(nativeClass) as KClass<*>).let { it.declaredFunctions.asSequence() }
//internal fun SiNativeClassifier.getNativeFunctions(): Sequence<Any> = implementation.getFunctions()

/** Termasuk yg mutable. */
internal actual fun getNativeProperties(nativeClass: Any): Sequence<Any> =
    (getNativeClass(nativeClass) as KClass<*>).declaredMemberProperties.asSequence()
//internal fun SiNativeClassifier.getNativeProperties(): Sequence<Any> = implementation.getProperties()

/** Tidak termasuk property yg immutable. */
internal actual fun getNativeMutableProperties(nativeClass: Any): Sequence<Any> =
    (getNativeClass(nativeClass) as KClass<*>).declaredMemberProperties.asSequence().filter { it.isMutableProperty }
//internal fun SiNativeClassifier.getNativeMutableProperties(): Sequence<Any> = implementation.getMutableProperties()

/**
 * Mengambil member yg dapat dipanggil dan dijadikan sbg [SiNativeCallable].
 * Member yg diambil termasuk fungsi dan semua properti.
 */
internal actual fun getNativeMembers(nativeClass: Any): Sequence<Any> =
    (getNativeClass(nativeClass) as KClass<*>).members.asSequence()

internal actual fun getNativeConstructors(nativeClass: Any): Sequence<Any> =
    (getNativeClass(nativeClass) as KClass<*>).constructors.asSequence()

/** Biasanya `this` merupakan fungsi yg punya paramter. */
internal actual fun getNativeParameters(nativeFunc: Any): Sequence<Any> =
    (nativeFunc as KCallable<*>).parameters.asSequence()

/** Mengambil immediate supertype dari `this`. */
internal actual fun getNativeSupertypes(nativeClass: Any): Sequence<Any> =
    (getNativeClass(nativeClass) as KClass<*>).supertypes.asSequence()


internal actual fun getParamIsOptional(nativeParam: Any): Boolean = when(nativeParam){
    is KParameter -> nativeParam.isOptional
    is Parameter -> false
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeParam::class, detMsg = "nativeParam bkn parameter.")
}
internal actual fun getParamType(nativeParam: Any): SiType = when(nativeParam){
    is KParameter -> nativeParam.type.si
    is Parameter -> nativeParam.type.kotlin.createType().si
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeParam::class, detMsg = "nativeParam bkn parameter.")
}

internal actual fun getParamKind(nativeParam: Any): SiParameter.Kind = when(nativeParam){
    is KParameter -> nativeParam.kind.si
    is Parameter -> SiParameter.Kind.VALUE
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeParam::class, detMsg = "nativeParam bkn parameter.")
}
//TODO <19 Agustus 2020> => untuk sementara JVM blum bisa ngambil defaultValue dari param.
internal actual fun getParamDefaultValue(nativeParam: Any): Any? = null

internal actual fun <T> getFuncCallBlock(nativeFuncHost: Any, nativeFunc: Any): (args: Array<out Any?>) -> T = when(nativeFunc){
    is KFunction<*> -> nativeFunc::call as (args: Array<out Any?>) -> T
    is Method -> { args: Array<out Any?> -> nativeFunc.invoke(nativeFunc, *args) as T }
    is Constructor<*> -> nativeFunc::newInstance as (args: Array<out Any?>) -> T
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeFunc::class, detMsg = "nativeFunc bkn fungsi.")
}

internal actual fun <T> getPropGetValueBlock(nativeProp: Any): (receivers: Array<out Any>) -> T = {
    when(nativeProp){
        is KProperty1<*, *> -> (nativeProp as KProperty1<Any, Any>).get(it.first())
        is Field -> nativeProp.get(it.first())
        else -> throw ReflexComponentExc(currentReflexedUnit = nativeProp::class, detMsg = "property bkn property.")
    } as T
}

internal actual fun <T> getPropSetValueBlock(nativeProp: Any): (receivers: Array<out Any>, value: T) -> Unit        = { receivers, value ->
    when(nativeProp){
        is KMutableProperty1<*, *> -> (nativeProp as KMutableProperty1<Any, T>).set(receivers.first(), value)
        is Field -> nativeProp.set(receivers.first(), value)
        else -> throw ReflexComponentExc(currentReflexedUnit = nativeProp::class, detMsg = "property bkn property.")
    } as T
}

internal actual fun getReturnType(nativeCallable: Any): SiType = when(nativeCallable){
    is KCallable<*> -> nativeCallable.returnType.si
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeCallable::class, detMsg = "nativeCallable bkn callable.")
}