@file:JvmName("_TypeFunJvm")

package sidev.lib.reflex

import kotlin.reflect.KClass


actual val KClass<*>.isPrimitiveArray: Boolean
    get()= java.componentType?.isPrimitive == true

