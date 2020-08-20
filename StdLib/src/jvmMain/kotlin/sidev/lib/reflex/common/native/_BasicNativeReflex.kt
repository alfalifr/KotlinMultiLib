@file:JvmName("BasicNativeReflexKtJvm")

package sidev.lib.reflex.common.native

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.number.or
import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.core.ReflexFactory
import sidev.lib.structure.data.value.Val
import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.isAccessible


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
internal actual fun getParamIsVararg(nativeParam: Any): Boolean = when(nativeParam){
    is KParameter -> nativeParam.isVararg
    is Parameter -> nativeParam.isVarArgs
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
    is KParameter -> nativeCallable.type.si
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeCallable::class, detMsg = "nativeCallable bkn callable.")
}
internal actual fun isTypeFinal(nativeType: Any): Boolean = true


internal actual fun getVisibility(nativeReflexUnit: Any): SiVisibility = when(nativeReflexUnit){
    is KClass<*> -> nativeReflexUnit.visibility?.si ?: SiVisibility.PUBLIC
    is KCallable<*> -> nativeReflexUnit.visibility?.si ?: SiVisibility.PUBLIC
    is Field -> nativeReflexUnit.modifiers.siVisibility
    is Method -> nativeReflexUnit.modifiers.siVisibility
    is Class<*> -> nativeReflexUnit.modifiers.siVisibility
    else -> SiVisibility.PUBLIC
}

private val Int.siVisibility: SiVisibility get()= getVisibility(this)


internal actual fun getIsAccessible(nativeReflexUnit: Any): Boolean = when(nativeReflexUnit){
    is KCallable<*> -> nativeReflexUnit.isAccessible
    is AccessibleObject -> nativeReflexUnit.isAccessible
    else -> false
}
internal actual fun setIsAccessible(nativeReflexUnit: Any, isAccessible: Boolean){
    when(nativeReflexUnit){
        is KCallable<*> -> nativeReflexUnit.isAccessible= isAccessible
        is AccessibleObject -> nativeReflexUnit.isAccessible= isAccessible
    }
}

internal actual fun getModifiers(nativeReflexUnit: Any): Int {
    val modifier= Val(0)
    when(nativeReflexUnit){
        is KCallable<*> -> {
            if(nativeReflexUnit.isAbstract)
                modifier or SiModifier.ABSTRACT.id
            if(nativeReflexUnit.isOpen)
                modifier or SiModifier.OPEN.id
        }
        is KClass<*> -> {
            if(nativeReflexUnit.isAbstract)
                modifier or SiModifier.ABSTRACT.id
            if(nativeReflexUnit.isOpen)
                modifier or SiModifier.OPEN.id
        }
        is KParameter -> {
            if(nativeReflexUnit.isOptional)
                modifier or SiModifier.OPTIONAL.id
            if(nativeReflexUnit.isVararg)
                modifier or SiModifier.VARARG.id
        }

        is Method -> {
            if(Modifier.isAbstract(nativeReflexUnit.modifiers))
                modifier or SiModifier.ABSTRACT.id
            if(!Modifier.isFinal(nativeReflexUnit.modifiers))
                modifier or SiModifier.OPEN.id
        }
        is Class<*> -> {
            if(Modifier.isAbstract(nativeReflexUnit.modifiers))
                modifier or SiModifier.ABSTRACT.id
            if(!Modifier.isFinal(nativeReflexUnit.modifiers))
                modifier or SiModifier.OPEN.id
        }
        is Parameter -> {
            if(nativeReflexUnit.isVarArgs)
                modifier or SiModifier.VARARG.id
        }
    }
    return modifier.value!!
}