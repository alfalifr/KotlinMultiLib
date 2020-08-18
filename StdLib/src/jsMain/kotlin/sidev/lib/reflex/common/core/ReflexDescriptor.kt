//@file:JvmName("ReflexDescriptorKtJvm")

package sidev.lib.reflex.common.core

import sidev.lib.console.prine
import sidev.lib.reflex.common.SiDescriptor
import sidev.lib.reflex.common.native.SiNative
import sidev.lib.reflex.js.JsCallable
import sidev.lib.reflex.js.JsMutableProperty
import sidev.lib.reflex.js.JsParameter
import sidev.lib.reflex.js.JsProperty
import kotlin.reflect.*


internal actual fun getNativeReflexDescription(nativeReflexUnit: Any): SiDescriptor.ElementType? = when(nativeReflexUnit){
    is JsMutableProperty<*, *> -> SiDescriptor.ElementType.MUTABLE_PROPERTY
    is JsProperty<*, *> -> SiDescriptor.ElementType.PROPERTY
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