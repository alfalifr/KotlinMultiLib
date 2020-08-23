@file:JvmName("ReflexFactoryKtJvm")

package sidev.lib.reflex.common.core

import sidev.lib.reflex.common.native.*
import java.lang.reflect.Executable
import java.lang.reflect.Field
import java.lang.reflect.Type
import kotlin.reflect.*


internal actual val SiNativeWrapper.nativeInnerName: String?
    get()= (getNativeClass(implementation) as KClass<*>).java.name

internal actual val SiNativeWrapper.nativeFullName: String? get()= when(val native= implementation){
    is KClass<*> -> native.qualifiedName ?: native.java.name
    is KCallable<*> -> native.name
    is KParameter -> native.name
    is KTypeParameter -> native.name
    is KType -> native.toString()
    is Class<*> -> native.name
    is Executable -> native.name
    is Field -> native.name
    is Type -> native.typeName
    else -> native.toString()
}
internal actual val SiNativeWrapper.nativeSimpleName: String? get()= when(val native= implementation){
    is KClass<*> -> native.simpleName
    is KCallable<*> -> native.name
    is KParameter -> native.name
    is KTypeParameter -> native.name
    is KType -> native.toString()
    is Class<*> -> native.simpleName
    is Executable -> native.name
    is Field -> native.name
    is Type -> native.typeName
    else -> native.toString()
}
