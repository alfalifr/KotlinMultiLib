@file:JvmName("_SiClassFunJvm")

package sidev.lib.reflex.full

import sidev.lib.exception.ReflexComponentExc
import sidev.lib.reflex.SiClass
import sidev.lib.reflex.SiFunction
import sidev.lib.reflex.inner.KotlinReflexConst
import sidev.lib.reflex.si
import sidev.lib.reflex.jvm.JvmReflexConst
import java.lang.reflect.AccessibleObject
import java.lang.reflect.Type
import kotlin.reflect.*
import kotlin.reflect.full.primaryConstructor

@get:JvmName("isPrimitive")
actual val SiClass<*>.isPrimitive: Boolean get()= when(val native= descriptor.native){
    is KClass<*> -> native.javaPrimitiveType != null
    is Class<*> -> native.isPrimitive
    else -> throw ReflexComponentExc(currentReflexedUnit = native ?: "<null>", detMsg = "`this` bkn merupakan golongan Class.")
}

@get:JvmName("isObjectArray")
actual val SiClass<*>.isObjectArray: Boolean get()= when(val native= descriptor.native){
    is KClass<*> -> native.toString() == KotlinReflexConst.K_ARRAY_CLASS_STRING
    is Class<*> -> native.isArray && native.componentType?.isPrimitive?.not() == true
    else -> throw ReflexComponentExc(currentReflexedUnit = native ?: "<null>", detMsg = "`this` bkn merupakan golongan Class.")
}

@get:JvmName("isPrimitiveArray")
actual val SiClass<*>.isPrimitiveArray: Boolean get()= when(val native= descriptor.native){
    is KClass<*> -> when(native){
        IntArray::class -> true
        LongArray::class -> true
        FloatArray::class -> true
        DoubleArray::class -> true
        CharArray::class -> true
        ShortArray::class -> true
        BooleanArray::class -> true
        ByteArray::class -> true
        else -> false
    }
    is Class<*> -> native.isArray && native.componentType?.isPrimitive == true
    else -> throw ReflexComponentExc(currentReflexedUnit = native ?: "<null>", detMsg = "`this` bkn merupakan golongan Class.")
}

@get:JvmName("isNativeInterface")
internal actual val SiClass<*>.isNativeInterface: Boolean get()= (when(val native= descriptor.native){
    is KClass<*> -> native.java
    is Class<*> -> native
    else -> null
})?.isInterface == true


@get:JvmName("primaryConstructor")
actual val <T: Any> SiClass<T>.primaryConstructor: SiFunction<T> get() = when(val native= descriptor.native){
    is KClass<*> -> {
        val nativeConstr= native.primaryConstructor
        constructors.find { it.descriptor.native == nativeConstr }!!
    }
    else -> throw ReflexComponentExc(currentReflexedUnit = this::class, detMsg = "Kelas native: \"$this\" bkn kelas.")
}

@get:JvmName("sealedSubclasses")
actual val SiClass<*>.sealedSubclasses: Sequence<SiClass<*>>
    get()= (descriptor.native as KClass<*>).sealedSubclasses.asSequence().map { it.si }


@get:JvmName("isAnonymous")
actual val SiClass<*>.isAnonymous: Boolean get()= (descriptor.native as KClass<*>).qualifiedName == null
