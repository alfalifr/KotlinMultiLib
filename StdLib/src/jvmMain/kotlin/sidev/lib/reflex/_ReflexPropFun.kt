@file:JvmName("ReflexPropFunKtJvm")

package sidev.lib.reflex

import kotlin.reflect.*


actual val <T: Any> KClass<T>.isPrimitive: Boolean
    get()= javaPrimitiveType != null