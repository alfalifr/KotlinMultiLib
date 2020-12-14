//@file:JvmName("ReflexDescriptorKtJvm")

package sidev.lib.reflex.core

import sidev.lib.reflex.SiDescriptor
import sidev.lib.reflex.js.*


internal actual fun getNativeReflexDescription(nativeReflexUnit: Any): SiDescriptor.ElementType? = when(nativeReflexUnit){
    is JsMutableProperty<*, *> -> SiDescriptor.ElementType.MUTABLE_PROPERTY
    is JsProperty<*, *> -> SiDescriptor.ElementType.PROPERTY
    is JsClass_<*> -> SiDescriptor.ElementType.CLASS
    is JsCallable<*> -> SiDescriptor.ElementType.FUNCTION
    is JsParameter -> SiDescriptor.ElementType.PARAMETER
    else -> null
}
/*
internal actual fun getNativeReflexDescription(nativeReflexUnit: SiNative): String?{
    prine("nativeReflexUnit= $nativeReflexUnit nativeReflexUnit::class ${nativeReflexUnit::class} nativeReflexUnit::class.supertypes= ${nativeReflexUnit::class.supertypes}")
    return when(nativeReflexUnit.implementation){
        is KClass<*> -> "class"
        is KFunction<*> -> "fun"
        is KMutableProperty<*> -> "var"
        is KProperty<*> -> "val"
        is KParameter -> "parameter"
        is KTypeParameter -> "type parameter"
        is KType -> null
        else -> null
    }
}
 */