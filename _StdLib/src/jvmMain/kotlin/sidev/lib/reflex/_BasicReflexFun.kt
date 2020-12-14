@file:JvmName("_BasicReflexFunJvm")

package sidev.lib.reflex

import kotlin.reflect.KClass

actual val KClass<*>.nativeFullName: String
    @JvmName("getNativeFullName") get()= java.name
actual val KClass<*>.nativeSimpleName: String
    @JvmName("getNativeSimpleName") get()= java.simpleName

/** Kelas native dari paltform-specific. */
actual val KClass<*>.native: Any
    @JvmName("getNativeClass") get()= java