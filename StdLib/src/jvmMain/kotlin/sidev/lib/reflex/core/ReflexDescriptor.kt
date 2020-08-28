@file:JvmName("ReflexDescriptorKtJvm")

package sidev.lib.reflex.core

import sidev.lib.reflex.SiDescriptor
import kotlin.reflect.*


//internal expect fun getNativeReflexDescription(nativeReflexUnit: Any): SiDescriptor.ElementType?
internal actual fun getNativeReflexDescription(nativeReflexUnit: Any): SiDescriptor.ElementType?{
//    prine("nativeReflexUnit= $nativeReflexUnit nativeReflexUnit::class ${nativeReflexUnit::class} nativeReflexUnit::class.supertypes= ${nativeReflexUnit::class.supertypes}")
    return when(nativeReflexUnit){
        is KClass<*> -> SiDescriptor.ElementType.CLASS
        is KFunction<*> -> SiDescriptor.ElementType.FUNCTION
        is KMutableProperty<*> -> SiDescriptor.ElementType.MUTABLE_PROPERTY
        is KProperty<*> -> SiDescriptor.ElementType.PROPERTY
        is KParameter -> SiDescriptor.ElementType.PARAMETER
        is KTypeParameter -> SiDescriptor.ElementType.TYPE_PARAMETER
        is KType -> SiDescriptor.ElementType.TYPE
        else -> null
    }
}
