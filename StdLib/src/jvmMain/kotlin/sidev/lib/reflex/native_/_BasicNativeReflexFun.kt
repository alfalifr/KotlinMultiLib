@file:JvmName("_BasicNativeReflexFunJvm")

package sidev.lib.reflex.native_

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.number.or
import sidev.lib.reflex.SiModifier
import sidev.lib.reflex.SiParameter
import sidev.lib.reflex.SiType
import sidev.lib.reflex.SiVisibility
import sidev.lib.reflex.inner.KotlinReflex
import sidev.lib.reflex.jvm.JvmReflexConst
import sidev.lib.reflex.jvm.si
import sidev.lib.structure.data.value.Val
import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaField


@get:JvmName("isDynamicEnabled")
actual val isDynamicEnabled: Boolean = false


internal actual fun getNativeAnnotations(nativeAnnotatedElement: Any): Sequence<Annotation> {
    if(nativeAnnotatedElement !is KAnnotatedElement)
        throw ReflexComponentExc(currentReflexedUnit = nativeAnnotatedElement::class, detMsg = "nativeAnnotatedElement bkn annotatedElement.")
    return nativeAnnotatedElement.annotations.asSequence()
}

/** Mengambil KClass dari [nativeClass]. */
internal actual fun <T: Any> getKClass(nativeClass: Any): KClass<T> = when(nativeClass){
    is KClass<*> -> nativeClass
    is Class<*> -> nativeClass.kotlin
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeClass::class, detMsg = "nativeClass bkn class.")
} as KClass<T>

/** `this.extension` dapat berupa apa saja. */
internal actual fun getNativeClass(any: Any): Any = if(any is KClass<*>) any else any::class

/** `this.extension` yg dimaksud adalah native class. */
internal actual fun getNativeFunctions(nativeClass: Any): Sequence<Any> =
    (getNativeClass(nativeClass) as KClass<*>).let { it.declaredFunctions.asSequence() }
//internal fun SiNativeClassifier.getNativeFunctions(): Sequence<Any> = implementation.getFunctions()

/** Termasuk yg mutable. */
internal actual fun getNativeProperties(nativeClass: Any): Sequence<Any>
        = (getNativeClass(nativeClass) as KClass<*>).declaredMemberProperties.asSequence()
//internal fun SiNativeClassifier.getNativeProperties(): Sequence<Any> = implementation.getProperties()

/** Tidak termasuk property yg immutable. */
internal actual fun getNativeMutableProperties(nativeClass: Any): Sequence<Any> =
    (getNativeClass(nativeClass) as KClass<*>).declaredMemberProperties.asSequence().filter { it.isNativeMutableProperty }
//internal fun SiNativeClassifier.getNativeMutableProperties(): Sequence<Any> = implementation.getMutableProperties()


/** Fungsi ini hanya mengambil declared field saja. */
internal actual fun getNativeFields(nativeClass: Any, nativeProperties: Sequence<Any>): Sequence<Any?> = when(nativeProperties.first()){
    is KProperty<*> -> (nativeProperties as Sequence<KProperty<*>>).map { it.javaField }
    is Field -> (nativeProperties as Sequence<Field>).asSequence()
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeProperties.first()::class, detMsg = "nativeProperties bkn property.")
}

/** Sama dg [getNativeFields], namun hanya mengambil field dari satu [nativeProperty]. */
internal actual fun getNativeField(nativeProperty: Any): Any? = when(nativeProperty){
    is KProperty<*> -> nativeProperty.javaField
    is Field -> nativeProperty
    else -> throw ReflexComponentExc(currentReflexedUnit = nativeProperty::class, detMsg = "nativeProperty bkn property.")
}

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
internal actual fun <T> getConstrCallBlock(nativeFuncHost: Any, nativeFunc: Any): (args: Array<out Any?>) -> T
        = getFuncCallBlock(nativeFuncHost, nativeFunc)
internal actual fun <T> getFuncDefaultCallBlock(nativeFuncHost: Any, nativeFunc: Any): ((args: Array<out Any?>) -> T)? {
    var isConstructor= false
    val funcName= when(nativeFunc){
        is KFunction<*> -> {
            nativeFunc.name.also {
                isConstructor= it == KotlinReflex.K_FUNCTION_CONSTRUCTOR_NAME
            }
        }
/*
        is Executable -> {
            isConstructor= nativeFunc is Constructor<*>
            nativeFunc.name
        }
 */
        else -> throw ReflexComponentExc(currentReflexedUnit = nativeFunc::class, detMsg = "nativeFunc bkn fungsi.")
    }

    return { args ->
        val javaClass= (when(nativeFuncHost){
            is KClass<*> -> nativeFuncHost.java
            is Class<*> -> nativeFuncHost
            else -> throw ReflexComponentExc(currentReflexedUnit = nativeFuncHost::class, detMsg = "nativeFuncHost bkn host yg valid untuk nativeFunc: \"$nativeFunc\".")
        })
        val slicedArgs= args.slice(1 until args.size).toTypedArray()
        (
            if(!isConstructor)
                javaClass.methods.find { JvmReflexConst.isDefaultOfFun(it, nativeFunc as KCallable<*>) }!!.invoke(args.first(), *slicedArgs)
            else
                javaClass.constructors.find { JvmReflexConst.isDefaultOfFun(it, nativeFunc as KCallable<*>) }!!.newInstance(*args)
        ) as T
    }
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

private val Int.siVisibility: SiVisibility get()= sidev.lib.reflex.jvm.getVisibility(this)

internal actual fun getModifiers(nativeReflexUnit: Any): Int {
    val modifier= Val(0)
    when(nativeReflexUnit){
        is KCallable<*> -> {
            if(nativeReflexUnit.isAbstract)
                modifier or SiModifier.ABSTRACT.id
            if(nativeReflexUnit.isOpen)
                modifier or SiModifier.OPEN.id
            if(nativeReflexUnit is KMutableProperty<*>)
                modifier or SiModifier.MUTABLE.id
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
        is Field -> {
            if(!Modifier.isFinal(nativeReflexUnit.modifiers))
                modifier or SiModifier.MUTABLE.id
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

/*
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
 */